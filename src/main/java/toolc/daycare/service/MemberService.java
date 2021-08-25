package toolc.daycare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toolc.daycare.repository.interfaces.member.StudentRepository;

@Service
public class MemberService {

    private final StudentRepository studentRepository;

    @Autowired MemberService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
}
