package vn.elca.training.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.dao.CompanyGroupRepository;
import vn.elca.training.model.dto.UserDto;
import vn.elca.training.model.entity.CompanyGroup;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.User;
import vn.elca.training.service.UserService;
import vn.elca.training.util.ApplicationMapper;

import java.time.LocalDate;
import java.util.List;

/**
 * @author gtn
 *
 */
@RestController
@RequestMapping("/users")
public class UserController extends AbstractApplicationController {

    @Autowired
    UserService userService;

    @Autowired
    CompanyGroupRepository companyGroupRepository;

    @GetMapping("/id/{id}")
    public User findOne(@PathVariable Long id) {
        return userService.findOne(id);
    }

    @GetMapping("/{username}")
    public UserDto findOne(@PathVariable String username) {
        User user = userService.findOne(username);
        return mapper.userToUserDto(user);
    }

    @PostMapping("/{username}/addTasks")
    public UserDto addTasks(@RequestBody List<Long> taskIds, @PathVariable String username) {
        if (CollectionUtils.isEmpty(taskIds)) {
            throw new IllegalArgumentException("Invalid request! List taskIds is empty");
        } else if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Invalid request! Username is blank");
        }

        User user = userService.addTasksToUser(taskIds, username);
        return mapper.userToUserDto(user);
    }

    @PutMapping("/update")
    public User update(@RequestBody User user) {
        if (user == null) {
            throw new IllegalArgumentException("Invalid request! User not found");
        }
        return userService.update(user);
    }

    @GetMapping("/add_data")
    public void addData(){
        User groupLeader1 = new User("QMV");
        User projectLeader1 = new User("HTV");
        Project projectNumber1 = new Project("Test project for complex query", LocalDate.now(), true);
        CompanyGroup companyGroup1 = new CompanyGroup("Test group for complex query");
//        projectLeader1.setProject(projectNumber1);
        projectNumber1.setProjectLeader(projectLeader1);
        projectNumber1.setCompanyGroup(companyGroup1);
        companyGroup1.addProject(projectNumber1);
        companyGroup1.setGroupLeader(groupLeader1);
        companyGroupRepository.save(companyGroup1);
    }
}
