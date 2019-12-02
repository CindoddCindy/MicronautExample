package cindy.example.repositori;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import cindy.example.model.User;

public interface UserRepositoryInterface {

    Long size();
    List<User> findAll (int page, int limit);
    User findById (@NotNull Long id);
    boolean save(@NotNull User user);
    boolean update(@NotNull Long id, @NotBlank String name, @NotBlank String password); // @NotNull int grade);
    boolean destroy(@NotNull Long id);
}