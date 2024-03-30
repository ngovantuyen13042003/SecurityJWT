package nvt.st.securityjwt.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginationResponse<T> {
    private List<T> data;
    private Integer pageNumber;
    private Integer pageSize;
    private String sortField;
    private String sortDir;
    private Integer totalElements;
    private Integer totalPages;
    private Boolean last;
}
