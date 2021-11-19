//package toolc.daycare.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//import toolc.daycare.domain.group.Area;
//import toolc.daycare.domain.group.Center;
//import toolc.daycare.domain.group.Class;
//import toolc.daycare.domain.group.Shuttle;
//import toolc.daycare.domain.member.*;
//import toolc.daycare.repository.interfaces.group.AreaRepository;
//import toolc.daycare.repository.interfaces.group.CenterRepository;
//import toolc.daycare.repository.interfaces.group.ClassRepository;
//import toolc.daycare.repository.interfaces.group.ShuttleRepository;
//import toolc.daycare.repository.interfaces.member.AdminRepository;
//import toolc.daycare.repository.interfaces.member.ParentsRepository;
//import toolc.daycare.repository.interfaces.member.StudentRepository;
//import toolc.daycare.repository.interfaces.member.TeacherRepository;
//
//import javax.annotation.PostConstruct;
//
//@RestController
//public class TestController {
//
//    private final CenterRepository centerRepository;
//    private final StudentRepository studentRepository;
//    private final TeacherRepository teacherRepository;
//    private final ClassRepository classRepository;
//    private final ParentsRepository parentsRepository;
//    private final ShuttleRepository shuttleRepository;
//    private final AreaRepository areaRepository;
//    private final AdminRepository adminRepository;
//    private final PasswordEncoder passwordEncoder;
//
//
//    @Autowired
//    public TestController(CenterRepository centerRepository,
//                          StudentRepository studentRepository,
//                          TeacherRepository teacherRepository,
//                          ClassRepository classRepository,
//                          ParentsRepository parentsRepository,
//                          ShuttleRepository shuttleRepository,
//                          AreaRepository areaRepository,
//                          AdminRepository adminRepository,
//                          PasswordEncoder passwordEncoder
//
//    ) {
//        this.centerRepository = centerRepository;
//        this.studentRepository = studentRepository;
//        this.teacherRepository = teacherRepository;
//        this.classRepository = classRepository;
//        this.parentsRepository = parentsRepository;
//        this.shuttleRepository = shuttleRepository;
//        this.areaRepository = areaRepository;
//        this.adminRepository = adminRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//
////    @GetMapping("/**")
//    @PostConstruct
//    public ResponseEntity<?> test() {
//        Center center = Center.builder()
//                .name("centerName")
//                .build();
//        centerRepository.save(center);
//
//        Class newClass = Class.builder()
//                .name("className")
//                .build();
//        newClass.setCenter(center);
//        classRepository.save(newClass);
//
//        Shuttle shuttle = Shuttle.builder()
//                .driverName("driverNames")
//                .build();
//        shuttleRepository.save(shuttle);
//
//        Area area = Area.builder()
//                .name("강동구")
//                .build();
//        areaRepository.save(area);
//
//
//        Student student = Student.builder()
//                .name("name")
//                .connectionNumber("010-1234-5678")
//                .sex(Sex.WOMAN)
//                .age(13)
//                .build();
//        student.setShuttle(shuttle);
//        student.setaClass(newClass);
//        student.setArea(area);
//        studentRepository.save(student);
//
//        Parents parents = Parents.builder()
//                .name("parentName")
//                .connectionNumber("010-1234-5678")
//                .loginId("loginId")
//                .sex(Sex.WOMAN)
//                .password("password")
//                .build();
//        parents.setStudents(student);
//        parentsRepository.save(parents);
//
//        Admin admin = Admin.builder()
//                .name("Admin")
//                .connectionNumber("010-1234-5678")
//                .loginId("admin")
//                .sex(Sex.WOMAN)
//                .password(passwordEncoder.encode("1234"))
//                .build();
//        admin.setExpoToken("ExponentPushToken[BjCfGWEn9W4MyIfi87FAry]");
//        adminRepository.save(admin);
//
//
//        return ResponseEntity.ok("hello");
//    }
//}