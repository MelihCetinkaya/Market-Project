package MarketProject.backend.repository;


import MarketProject.backend.entity.Comment;
import MarketProject.backend.entity.enums.CommentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    //@Query("SELECT c FROM Comment c WHERE c.commentType = 'market'")
   // List<Comment> findAllMarketComments();

    @Query("SELECT c FROM Comment c WHERE c.commentType = :commentType AND c.product.productId = :productId") //******
    List<Comment> findProductCommentsByProductId(@Param("productId") Long productId,
                                                 @Param("commentType") CommentType commentType);


    @Query("SELECT c FROM Comment c WHERE c.commentType = :commentType")  // not used
    List<Comment> findByCommentType(@Param("commentType") CommentType commentType);
}
