package site.metacoding.dbproject.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.dbproject.domain.post.Post;
import site.metacoding.dbproject.domain.post.PostRepository;
import site.metacoding.dbproject.domain.user.User;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> 글목록보기(Integer page){

        PageRequest pq = PageRequest.of(page, 3, Sort.by(Direction.DESC, "id")); // 페이지를 원래 계산해야 되는데 시작 번호를 알아서 찾아준다.
       
        return postRepository.findAll(pq);
    }

    // 글 상세보기, 글수정 페이지 가기에 필요함
    public Post 글상세보기(Integer id){

        Optional<Post> postOp = postRepository.findById(id);
      
        if (postOp.isPresent()) { // null이 아니면
            Post postEntity = postOp.get(); // entity에 postOp 담기

            return postEntity; // 모델에 담기도록 엔티티 주기
        } else {
            return null;
        }
        
    }

    @Transactional
    public void 글수정하기(){ // wirte이기 때문에 트랜잭션이다.
        // 스택이 끝날 때 커밋된다. 더티체킹이 되는 셈.

    }
    
    // SELECT해서 가야하기 때문에, 글 상세보기를 재사용하면 된다.
    // public void 글수정페이지가기(){

    // }
    
    @Transactional
    public void 글삭제하기(Integer id){
        postRepository.deleteById(id); // void이기 때문에 실패했을 때 내부적으로 예외가 터짐

    }

    @Transactional
    public void 글쓰기(Post post, User principal){
        post.setUser(principal); // FK 추가하는 것, 글쓴 사람이 누군지 세션 더하기
        // INSERT into post(title, content, userId) VALUES(사용자, 사용자, 세션오브젝트의 PK);

        postRepository.save(post); // 돌려줄 필요 없음
    }
}
