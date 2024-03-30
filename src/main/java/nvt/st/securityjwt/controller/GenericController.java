package nvt.st.securityjwt.controller;

import nvt.st.securityjwt.constants.AppConstants;
import nvt.st.securityjwt.dto.BaseDTO;
import nvt.st.securityjwt.model.BaseEntity;
import nvt.st.securityjwt.payload.BaseResponse;
import nvt.st.securityjwt.payload.PaginationResponse;
import nvt.st.securityjwt.service.IAbstractBaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class GenericController<TModel extends BaseEntity, TDto extends BaseDTO, TDtoRequest extends TDto> {
    private final IAbstractBaseService<TModel, TDto> baseService;

    public GenericController(IAbstractBaseService<TModel, TDto> baseService) {
        this.baseService = baseService;
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<TDto>> findAll(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer page,
            @RequestParam(value = "limit", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer limit,
            @RequestParam(value = "sortField", defaultValue = AppConstants.DEFAULT_SORT_FIELD, required = false) String sortField,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        PaginationResponse<TDto> response = baseService.findAll(page, limit, sortField, sortDir);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TDto> findById(@PathVariable("id") Long id) {
        TDto response = baseService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@ModelAttribute TDtoRequest tDtoRequest) {
        return ResponseEntity.ok(
                baseService.saveOrUpdate(tDtoRequest)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable("id") Long id, TDtoRequest tDtoRequest) {
        tDtoRequest.setId(id);
        return ResponseEntity.ok(
                this.baseService.saveOrUpdate(tDtoRequest)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteById(@PathVariable("id")Long id) {
        return ResponseEntity.ok(
                this.baseService.deleteById(id)
        );
    }



}
