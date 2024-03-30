package nvt.st.securityjwt.service.impl;

import nvt.st.securityjwt.dto.BaseDTO;
import nvt.st.securityjwt.exception.ResourceExistsException;
import nvt.st.securityjwt.exception.ResourceNotFoundException;
import nvt.st.securityjwt.model.BaseEntity;
import nvt.st.securityjwt.payload.BaseResponse;
import nvt.st.securityjwt.payload.PaginationResponse;
import nvt.st.securityjwt.repository.AbstractBaseRepository;
import nvt.st.securityjwt.service.IAbstractBaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public abstract class AbstractBaseServiceImpl<TModel extends BaseEntity, TDto extends BaseDTO> implements IAbstractBaseService<TModel, TDto> {
    private final AbstractBaseRepository<TModel> baseRepository;
    private final Class<TModel> entityClass;
    private final Class<TDto> dtoClass;

    private ModelMapper modelMapper;

    public AbstractBaseServiceImpl(AbstractBaseRepository<TModel> baseRepository,
                                   Class<TModel> entityClass,
                                   Class<TDto> dtoClass) {
        this.baseRepository = baseRepository;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public PaginationResponse<TDto> findAll(Integer pageNumber, Integer size, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber-1, size, sort);
        Page<TModel> page = baseRepository.findAll(pageable);
        List<TModel> tModels = page.getContent();
        return PaginationResponse.<TDto>builder()
                .data(tModels.stream().map(this::transformEntityToDto).collect(Collectors.toList()))
                .pageNumber(page.getNumber()+1)
                .pageSize(page.getSize())
                .sortField(sortField)
                .sortDir(sortDir)
                .totalElements((int)page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @Override
    public BaseResponse saveOrUpdate(TDto element) {
        try{
            if(isExists(element))return null;

            TModel entity = this.modelMapper.map(element, entityClass);
            String operationText = entity.getId()!=null ? "Update" : "Create";
            this.baseRepository.save(entity);
            return new BaseResponse(200, String.format("%s %s success", operationText, entityClass.getSimpleName().toLowerCase()));

        }catch (ResourceExistsException exception){
            throw  exception;
        }
    }

    @Override
    public TDto findById(Long id) {
        TModel model = baseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(entityClass.getSimpleName(), "id", String.valueOf(id))
        );
        return this.modelMapper.map(model, dtoClass);
    }

    @Override
    public BaseResponse deleteById(Long id) {
        try {
            baseRepository.deleteById(id);
            return new BaseResponse(200, "Delete "+ entityClass.getSimpleName().toLowerCase()+ " success");
        }catch (ResourceNotFoundException e) {
            throw e;
        }
    }

    @Override
    public boolean isExists(TDto element) throws ResourceExistsException {
        return false;
    }

    @Override
    public TDto transformEntityToDto(TModel element) {
        return modelMapper.map(element, dtoClass);
    }
}














