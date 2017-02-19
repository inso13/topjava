package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.ExceptionInfoHandler;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * User: grigory.kislin
 */
@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @Autowired
    MessageSource messageSource;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@Valid UserTo userTo) {
        try {
            if (userTo.isNew()) {
                super.create(UserUtil.createNewFromTo(userTo));
            } else {
                super.update(userTo);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(messageSource.getMessage("users.exists", null, Locale.getDefault()));
        }
    }

    @PostMapping(value = "/{id}")
    public void enabled(@PathVariable("id") int id, @RequestParam("enabled") boolean enabled) {
        super.enable(id, enabled);
    }
}
