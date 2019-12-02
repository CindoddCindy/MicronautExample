package cindy.example.repositori;

import java.util.Date;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import cindy.example.model.User;

@Singleton
public class UserRepository implements UserRepositoryInterface {

    @PersistenceContext
    private EntityManager manager;

    public UserRepository(@CurrentSession EntityManager manager){
        this.manager = manager;
    }

    @Override
    @Transactional(readOnly = true)
    public Long size() {
        Long count = manager.createQuery("select count(*) from User where deleted_at IS NULL", Long.class).getSingleResult();
        return count;
    }

    @Override
    @Transactional
    public List<User> findAll(int page, int limit) {
        TypedQuery<User> query = manager
                .createQuery("from User where deleted_at IS NULL", User.class)
                .setFirstResult(page > 1 ? page * limit - limit : 0)
                .setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(@NotNull Long id) {
        User query = manager.find(User.class, id);
        return query;
    }

    @Override
    @Transactional
    public boolean save(@NotNull User user) {
        try {
            manager.persist(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean update(@NotNull Long id, String name, String password) {
        try {

            User c = manager.find(User.class, id);
            if (name.equals("-")==false) c.setName(name);
            if (password.equals("-")==false) c.setPassword(password);
          //  if (grade != 0) c.setGrade(grade);
            c.setUpdated_At(new Date());
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean destroy(@NotNull Long id) {
        try {
            User c = manager.find(User.class, id);
            c.setDeleted_At(new Date());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}