package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.NamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private Comparator<User> comparator = Comparator.comparing(NamedEntity::getName);

    public static final int USER_ID=1;

    {
        repository.put(1, new User(1, "User", "user@yandex.ru", "111", Role.ROLE_USER ));
        repository.put(2, new User(2, "Admin", "admin@yandex.ru", "222", Role.ROLE_ADMIN ));
    }


    @Override
    public boolean delete(int id) {

        if (repository.containsKey(id)) {repository.remove(id); LOG.info("delete " + id); return true; }

        else return false;

    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) user.setId(counter.incrementAndGet());
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        List<User> list = new ArrayList<>();
        list.addAll(repository.values());
        list.sort(comparator);
        return list;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);

        for (Map.Entry<Integer, User> pair: repository.entrySet())
        {
        if (pair.getValue().getEmail().equals(email)) return repository.get(pair.getKey());}

        return null;
    }
}
