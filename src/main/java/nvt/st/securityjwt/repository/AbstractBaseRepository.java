package nvt.st.securityjwt.repository;

import nvt.st.securityjwt.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

// @NoRepositoryBean : đánh dấu đây là 1 interface or abstract không phải là 1 repo cụ thể.
// mà chỉ là một tệp chia sẽ chung cho các repository khác kế thừa

// <T extends BaseEntity> --> để implement interface này thì các entity(T) phải là lớp con của BaseEntity
@NoRepositoryBean
public interface AbstractBaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
}
