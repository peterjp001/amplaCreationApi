package com.ampla.api.mis.service.impl;

import com.ampla.api.exception.DataAlreadyExistException;
import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.dto.EmployeeAsTeacherDTO;
import com.ampla.api.mis.dto.EmployeeFunctionDTO;
import com.ampla.api.mis.dto.EmployeeUserDTO;
import com.ampla.api.mis.dto.ResponseEmployeeUser;
import com.ampla.api.mis.entities.Course;
import com.ampla.api.mis.entities.Employee;
import com.ampla.api.mis.entities.Function;
import com.ampla.api.mis.repository.CourseRepository;
import com.ampla.api.mis.repository.EmployeeRepository;
import com.ampla.api.mis.repository.FunctionRepository;
import com.ampla.api.mis.service.EmployeeService;
import com.ampla.api.security.entities.User;
import com.ampla.api.security.service.AccountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    
    protected static final Log logger = LogFactory.getLog(EmployeeServiceImpl.class);
    private final EmployeeRepository emplRepo;
    private final AccountService accountService;
    private final FunctionRepository functionRepository;
    private final CourseRepository courseRepository;


    public EmployeeServiceImpl(EmployeeRepository emplRepo, AccountService accountService1, FunctionRepository functionRepository, CourseRepository courseRepository) {
        this.emplRepo = emplRepo;
        this.accountService = accountService1;
        this.functionRepository = functionRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public String createCodeEmployee(String firstName, String lastName, String phone) {
        return (
                firstName.substring(0,2)+phone.substring(0,4)+"-"+lastName.substring(0,2)+phone.substring(4,8)
        ).toUpperCase();
    }

    @Override
    public Employee getByPhone(String phone) {
        return emplRepo.findByPhone(phone);
    }

    @Override
    public Employee getByFirstNameAndLastName(String firstName, String lastName) throws DataNotFoundException {
        return Optional.of(emplRepo.findByFirstNameAndLastName(firstName, lastName))
                .orElseThrow(()->new DataNotFoundException("Employee '"+firstName+" "+lastName+"' not found"));
    }

//    @Override
//    public Boolean testPhone(String phone) {
//        Pattern pattern = Pattern.compile("^+509");
//        Matcher matcher = pattern.matcher(phone);
//        return  matcher.matches();
//    }


    @Override
    public Employee saveEmployee(Employee emp) throws DataAlreadyExistException {
        Optional<Employee> empPhoneExist = Optional.ofNullable(getByPhone(emp.getPhone()));
        Optional<Employee> empEmailExist = Optional.ofNullable(emplRepo.findByEmail(emp.getEmail()));
        if (empPhoneExist.isPresent()){
            throw new DataAlreadyExistException("Le numéro "+emp.getPhone()+ " existe déjà");
        }
        if (empEmailExist.isPresent()){
            throw new DataAlreadyExistException("L'email "+emp.getEmail()+ " existe déjà");
        }

        Optional<Employee> empNifExist = Optional.ofNullable(emplRepo.findByNif(emp.getNif()));
        if (empNifExist.isPresent()){
            throw new DataAlreadyExistException("Le NIF "+emp.getNif()+ " existe déjà");
        }

        emp.setCodeEmployee(createCodeEmployee(emp.getFirstName(), emp.getLastName(), emp.getPhone()));
        return emplRepo.save(emp);
    }

//    @Override
//    public void linkEmployeeToUser(Long idUser, Long idEmployer) throws DataNotFoundException {
//        Optional<User> user = accountService.getUserById(idUser);
//        Optional<Employee> emp = emplRepo.findById(idEmployer);
//        if (user.isPresent() && emp.isPresent()) {
//            User u = user.get();
//            Employee e = emp.get();
//            e.setUser(u);
//            System.out.println(u.getEmployee());
//            emplRepo.save(e);
//        }
//    }


    @Override
    public List<Employee> listEmployee() {
        return emplRepo.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return emplRepo.findById(id);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeFunctionDTO efDTO) throws DataNotFoundException, DataAlreadyExistException {

        Optional<Employee> emp = this.getEmployeeById(id);

        if(emp.isEmpty()){
            throw  new DataNotFoundException("Employee with id "+id+ " not found");
        }
        Employee updateEmp = emp.get();

        if(efDTO.getFirstName() != null){
            updateEmp.setFirstName(efDTO.getFirstName());
        }
        if (efDTO.getLastName() != null){
            updateEmp.setLastName(efDTO.getLastName());
        }
        if(efDTO.getSexe() != null){
            updateEmp.setSexe(efDTO.getSexe());
        }
        if(efDTO.getNif() != null){
            updateEmp.setNif(efDTO.getNif());
        }
        if(efDTO.getEmail() != null){
            updateEmp.setEmail(efDTO.getEmail());
        }
        if(efDTO.getPhone() != null){
            Optional<Employee> empPhoneExist = Optional.ofNullable(getByPhone(efDTO.getPhone()));
            if (empPhoneExist.isPresent()){
                throw new DataAlreadyExistException("Phone "+efDTO.getPhone()+ " already exist");
            }
            updateEmp.setPhone(efDTO.getPhone());
        }
        if(efDTO.getBirthDate() != null){
            updateEmp.setBirthDate(efDTO.getBirthDate());
        }

        return emplRepo.save(updateEmp);
    }

    @Override
    public void deleteEmployee(Long id) {
        emplRepo.deleteById(id);
    }

    @Override
    public Employee newEmployeeWithNoAccount(EmployeeFunctionDTO euDTO) throws DataNotFoundException, DataAlreadyExistException {

        Employee emp = new Employee();

        emp.setCodeEmployee(createCodeEmployee(euDTO.getFirstName(), euDTO.getLastName(), euDTO.getPhone()));
        emp.setFirstName(euDTO.getFirstName());
        emp.setLastName(euDTO.getLastName());
        emp.setSexe(euDTO.getSexe());

        Optional<Employee> empPhoneExist = Optional.ofNullable(getByPhone(euDTO.getPhone()));
        if (empPhoneExist.isPresent()){
            throw new DataAlreadyExistException("Le numéro "+euDTO.getPhone()+ " existe déjà");
        }

        Optional<Employee> empEmailExist = Optional.ofNullable(emplRepo.findByEmail(euDTO.getEmail()));
        if (empEmailExist.isPresent()){
            throw new DataAlreadyExistException("L'email "+euDTO.getEmail()+ " existe déjà");
        }

        Optional<Employee> empNifExist = Optional.ofNullable(emplRepo.findByNif(euDTO.getNif()));
        if (empNifExist.isPresent()){
            throw new DataAlreadyExistException("Le NIF "+euDTO.getNif()+ " existe déjà");
        }

        emp.setEmail(euDTO.getEmail());
        emp.setPhone(euDTO.getPhone());
        emp.setBirthDate(euDTO.getBirthDate());
        emp.setNif(euDTO.getNif());

        if(euDTO.getFunctions().size() >0){
            euDTO.getFunctions().forEach(function -> {
                Function func = functionRepository.findByFunctionName(function.getFunctionName());
                emp.getFunctions().add(func);
            });
        }else{
            throw new DataNotFoundException("Function Required");
        }

        return saveEmployee(emp);
    }

    @Override
    public ResponseEmployeeUser newEmployeeWithAccount(EmployeeUserDTO euDTO) throws DataAlreadyExistException, DataNotFoundException {
        User u = new User();
        Employee emp = new Employee();

        u.setUsername(euDTO.getUsername());
        u.setPassword(euDTO.getPassword());
        u.setRoles(euDTO.getRoles());
        User newUser = accountService.addNewUser(u);

        emp.setCodeEmployee(createCodeEmployee(euDTO.getFirstName(), euDTO.getLastName(), euDTO.getPhone()));
        emp.setFirstName(euDTO.getFirstName());
        emp.setLastName(euDTO.getLastName());
        emp.setSexe(euDTO.getSexe());

        Optional<Employee> empPhoneExist = Optional.ofNullable(getByPhone(euDTO.getPhone()));
        if (empPhoneExist.isPresent()){
            throw new DataAlreadyExistException("Phone "+euDTO.getPhone()+ " already exist");
        }

        Optional<Employee> empEmailExist = Optional.ofNullable(emplRepo.findByEmail(euDTO.getEmail()));
        if (empEmailExist.isPresent()){
            throw new DataAlreadyExistException("L'email "+euDTO.getEmail()+ " existe déjà");
        }

        Optional<Employee> empNifExist = Optional.ofNullable(emplRepo.findByNif(euDTO.getNif()));
        if (empNifExist.isPresent()){
            throw new DataAlreadyExistException("Le NIF "+euDTO.getNif()+ " existe déjà");
        }

        emp.setEmail(euDTO.getEmail());
        emp.setPhone(euDTO.getPhone());
        emp.setBirthDate(euDTO.getBirthDate());
        emp.setNif(euDTO.getNif());
        emp.setUser(newUser);
        Employee newEmp = emplRepo.save(emp);
        if (euDTO.getFunctions().size() > 0) {
            euDTO.getFunctions().forEach(function -> {
                Function func = functionRepository.findByFunctionName(function.getFunctionName());
                newEmp.getFunctions().add(func);
            });
        }

        return new ResponseEmployeeUser(newEmp, newUser);
    }

    @Override
    public User userToExistingEmployee(User user, String codeEmployee) throws DataNotFoundException {

        Optional<Employee> emp = Optional.ofNullable(emplRepo.findEmployeeByCodeEmployee(codeEmployee));
        if (emp.isEmpty()) throw new DataNotFoundException("L'employé avec le code "+codeEmployee+" n'existe pas");
        Employee employee = emp.get();
        if(employee.getUser() != null)
            throw new DataAlreadyExistException("L'employé avec le code " + codeEmployee + " a déjà un compte");
        User newUser = accountService.addNewUser(user);
        employee.setUser(newUser);
          emplRepo.save(employee);
        return  newUser;
    }

    @Override
    public List<ResponseEmployeeUser> allEmployeeWithUserAccount() {

        List<User> users = accountService.listUsers();
        List<ResponseEmployeeUser>  resEmpUser = new ArrayList<>();

        for (User u: users) {
            Employee emp = emplRepo.findEmployeeByUser(u);
            if (emp != null){
                ResponseEmployeeUser  reu = new ResponseEmployeeUser();
                reu.setUserData(u);
                reu.setEmployeeData(emp);
                resEmpUser.add(reu);
            }
        }

        return resEmpUser;
    }

    @Override
    public void addFunctionToEmployee(Long idEmployee, String functionName) throws DataNotFoundException {
        Optional<Employee> emp = emplRepo.findById(idEmployee);
        if(emp.isEmpty()){
            throw new DataNotFoundException("Employee with id "+idEmployee+ " not found");
        }
        Employee employee = emp.get();

        Function func = functionRepository.findByFunctionName(functionName);

        if(!employee.getFunctions().contains(func)){
            logger.info("Role "+func.getFunctionName()+ " added To "+ employee.getFirstName() +" "+ employee.getFirstName());
            employee.getFunctions().add(func);
        }else{
            logger.error("Can't add Role "+func.getFunctionName()+ " added To "+ employee.getFirstName() +" "+ employee.getFirstName());
        }

    }


    @Override
    public void removeFunctionToEmployee(Long idEmployee, String functionName) throws DataNotFoundException {
        Optional<Employee> emp = emplRepo.findById(idEmployee);
        if(emp.isEmpty()){
            throw new DataNotFoundException("Employee with id "+idEmployee+ " not found");
        }
        Employee employee = emp.get();

        Function func = functionRepository.findByFunctionName(functionName);

        if(employee.getFunctions().contains(func)){
            logger.info("Role "+func.getFunctionName()+ " added To "+ employee.getFirstName() +" "+ employee.getFirstName());
            employee.getFunctions().remove(func);
        }else{
            logger.error("Can't add Role "+func.getFunctionName()+ " added To "+ employee.getFirstName() +" "+ employee.getFirstName());
        }
    }

    @Override
    public EmployeeAsTeacherDTO addTeacher(EmployeeAsTeacherDTO eatDTO) throws DataNotFoundException {
        Employee emp = new Employee();


        if(eatDTO.getFunctions().size() >0){
            for (Function func: eatDTO.getFunctions()) {
                if(!func.getFunctionName().equals("Professeur"))
                    throw new DataNotFoundException("Employee should have TEACHER as a function");
            }
            eatDTO.getFunctions().forEach(function -> {
                Function func = functionRepository.findByFunctionName(function.getFunctionName());
                emp.getFunctions().add(func);
            });
        }else{
            throw new DataNotFoundException("Function Required");
        }

        if(eatDTO.getCourses().size() >0){
            eatDTO.getCourses().forEach(c -> {
                Course course = courseRepository.findByCourseName(c.getCourseName());
                emp.getCourse().add(course);
            });

        }else{
            throw new DataNotFoundException("Course Required");
        }

        emp.setCodeEmployee(createCodeEmployee(eatDTO.getFirstName(), eatDTO.getLastName(), eatDTO.getPhone()));
        emp.setFirstName(eatDTO.getFirstName());
        emp.setLastName(eatDTO.getLastName());
        emp.setSexe(eatDTO.getSexe());
        emp.setEmail(eatDTO.getEmail());
        Optional<Employee> empPhoneExist = Optional.ofNullable(getByPhone(eatDTO.getPhone()));
        if (empPhoneExist.isPresent()){
            throw new DataAlreadyExistException("Le num2ro "+eatDTO.getPhone()+ " existe déjà");
        }
        emp.setPhone(eatDTO.getPhone());
        emp.setBirthDate(eatDTO.getBirthDate());
        emp.setNif(eatDTO.getNif());

        Employee e = saveEmployee(emp);

        eatDTO.setFunctions(e.getFunctions());
        eatDTO.setCourses(e.getCourse());

        return eatDTO;
    }

    @Override
    public List<Employee> listEmployeeByFunctionName(String functionName) {
        return emplRepo.findEmployeeByFunctionsFunctionName(functionName);
    }

    @Override
    public List<Employee> listEmployeeByCourseName(String courseName) {
        return emplRepo.findEmployeeByCourseCourseName(courseName);
    }

    @Override
    public Employee getEmployeeByCode(String codeEmployee) throws DataNotFoundException {
        return Optional.ofNullable(emplRepo.findEmployeeByCodeEmployee(codeEmployee)).orElseThrow(
                ()->new DataNotFoundException("Employee with Code "+codeEmployee+" not exist!")
        );
    }

    @Override
    public List<Employee> listEmployeeByCourseId(Long id) {
        return emplRepo.findEmployeeByCourseId(id);
    }
}
