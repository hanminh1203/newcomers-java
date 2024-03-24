package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.elca.training.model.entity.CompanyGroup;
import vn.elca.training.model.exception.GroupNotFoundException;
import vn.elca.training.repository.CompanyGroupRepository;
import vn.elca.training.service.CompanyGroupService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyGroupServiceImpl implements CompanyGroupService {

    @Autowired
    CompanyGroupRepository companyGroupRepository;
    @Override
    public CompanyGroup findById(Long id) throws GroupNotFoundException {
        Optional<CompanyGroup> optionalGroup = companyGroupRepository.findById(id);
        if(optionalGroup.isPresent()){
            return optionalGroup.get();
        }
        else {
            throw new GroupNotFoundException(id);
        }
    }

    @Override
    public List<Long> getAllGroups() {
        // Group has no name property, temporally use its id as name,
        List<CompanyGroup> companyGroups = companyGroupRepository.findAll();
        List<Long> companyGroupsId = new ArrayList<>();
        for (CompanyGroup c: companyGroups){
            companyGroupsId.add(c.getId());
        }
        return companyGroupsId;
    }
}
