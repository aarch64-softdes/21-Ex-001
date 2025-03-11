package com.tkpm.sms.service;

import com.tkpm.sms.dto.StudentRequest;
import com.tkpm.sms.dto.StudentResponse;
import com.tkpm.sms.entity.Student;
import com.tkpm.sms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public void addNewStudent(StudentRequest studentRequest) {
        // create UUID
        String id = java.util.UUID.randomUUID().toString();
        Student student = Student.builder()
                    .id(id)
                    .studentId(studentRequest.getStudentId())
                    .name(studentRequest.getName())
                    .dob(studentRequest.getDob())
                    .gender(studentRequest.getGender())
                    .faculty(studentRequest.getFaculty())
                    .course(studentRequest.getCourse())
                    .program(studentRequest.getProgram())
                    .email(studentRequest.getEmail())
                    .address(studentRequest.getAddress())
                    .phone(studentRequest.getPhone())
                    .status(studentRequest.getStatus())
                    .build();

        studentRepository.save(student);
    }

    public void deleteStudentById(String id) {
        studentRepository.deleteById(id);
    }

    public void updateStudent(String id, StudentRequest studentRequest) {
        var student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        student.setStudentId(studentRequest.getStudentId());
        studentRepository.save(student);
    }

    public List<StudentResponse> getStudentsByNameOrId(String find) {
        return studentRepository.getStudentsByNameOrId(find).stream().map(this::mapToResponse).toList();
    }

    private StudentResponse mapToResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .studentId(student.getStudentId())
                .name(student.getName())
                .dob(student.getDob())
                .gender(student.getGender())
                .faculty(student.getFaculty())
                .course(student.getCourse())
                .program(student.getProgram())
                .email(student.getEmail())
                .address(student.getAddress())
                .phone(student.getPhone())
                .status(student.getStatus())
                .build();
    }
}
