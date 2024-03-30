package nvt.st.securityjwt.service;

import nvt.st.securityjwt.dto.BaseDTO;
import nvt.st.securityjwt.exception.ResourceExistsException;
import nvt.st.securityjwt.model.BaseEntity;
import nvt.st.securityjwt.payload.BaseResponse;
import nvt.st.securityjwt.payload.PaginationResponse;

public interface IAbstractBaseService<TModel extends BaseEntity, TDto extends BaseDTO> {
    PaginationResponse<TDto> findAll(Integer pageNumber, Integer size, String sortField, String sortDir);
    BaseResponse saveOrUpdate(TDto element);
    BaseResponse deleteById(Long id);
    TDto findById(Long id);
    boolean isExists(TDto element) throws ResourceExistsException;
    TDto transformEntityToDto(TModel element);

}
