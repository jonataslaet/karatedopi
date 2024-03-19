package br.com.karatedopi.repositories;

import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.UserDetailsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.email = :email")
    Long countUsersByEmail(@Param("email") String email);

    @Query(nativeQuery = true, value = """
			SELECT tb_user.id, tb_user.email AS username, tb_user.firstname, tb_user.lastname, tb_user.password, 
			tb_role.id AS roleId, tb_role.authority, tb_user.status
			FROM tb_user
			INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id
			INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id
			WHERE tb_user.email = :email
		""")
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);

	@Query(value = """
        SELECT user FROM User user WHERE :search IS NULL OR TRIM(CAST(:search AS text)) = '' OR
        LOWER(user.firstname) like LOWER(CONCAT('%', CAST(:search AS text), '%'))
        """)
	Page<User> findAllBySearchContent(@Param("search") String search, Pageable pagination);

}
