package com.anotites.service;

import com.anotites.DAO.PersonDao;
import com.anotites.pojos.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class PersonServiceNew {
    @Autowired
    private PersonDao personDao;

    @Autowired
    private TransactionTemplate transactionTemplate;

//    @Transactional
//    public void createPerson(Person person) {
//        personDao.save(person);
//        // Если здесь возникнет исключение, транзакция откатится
//    }

    // Убрали @Transactional, теперь управляем программно
    public void createPerson(Person person) {
        transactionTemplate.execute(status -> {
            try {
                // Вся бизнес-логика внутри этой лямбды выполняется в одной транзакции
                personDao.save(person);

                // Если нужно, здесь можно вызвать другие DAO методы

                return null; // можно вернуть результат, если нужно
            } catch (Exception e) {
                // При возникновении исключения откатываем транзакцию
                status.setRollbackOnly();
                throw e; // пробрасываем исключение дальше
            }
        });
    }
}
