package study.spring.webflux.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import study.spring.webflux.model.PersonEntity;
import study.spring.webflux.repository.PersonRepository;

/**
 * @author: lihong58
 * @date: 2017/11/1
 * description: ${DESCRIPTION}
 */
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final PersonRepository personRepository;

    public DevBootstrap(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {

        PersonEntity lihong = new PersonEntity("李宏", "15011189325", "123456");
        PersonEntity test2 = new PersonEntity("测试人员1", "12345678901", "654321");
        personRepository.save(lihong);
        personRepository.save(test2);

    }
}
