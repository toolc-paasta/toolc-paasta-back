package toolc.daycare.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.member.Student;
import toolc.daycare.repository.interfaces.member.StudentRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {
  private final StudentRepository studentRepository;

  public List<Student> getStudentsByClassId(Long id) {
    return studentRepository.findByaClassId(id);
  }
}
