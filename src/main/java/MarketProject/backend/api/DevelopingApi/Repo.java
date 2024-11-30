package MarketProject.backend.api.DevelopingApi;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repo extends ReactiveMongoRepository <Model1,Long> {

}
