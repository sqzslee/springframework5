package study.spring.webflux.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import study.spring.webflux.model.PersonEntity;

/**
 * @author: lihong58
 * @date: 2017/11/1
 * description: 个人信息数据处理
 * 直接继承ReactiveCrudRepository会报“No property saveAll found for type”的错误。
 * 猜测原因为：spring-data仅对MongoDB、Apache Cassandra以及Redis提供了反应式支持
 * 此处使用mongodb
 */
public interface PersonRepository extends ReactiveCrudRepository<PersonEntity, ObjectId> {

}
// @Repository
// public class PersonRepository {
//     private final List<PersonEntity> users = Arrays.asList(new PersonEntity("李宏", "15011189325", "123456"), new PersonEntity("测试人员1", "12345678901", "654321"));
//
//     public Mono<PersonEntity> findById(Long id) {
//         return Mono.justOrEmpty(users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null));
//     }
//
//     public Flux<PersonEntity> findAll() {
//         return Flux.fromIterable(users);
//     }
// }
