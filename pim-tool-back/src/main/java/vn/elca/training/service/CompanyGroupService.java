package vn.elca.training.service;

import vn.elca.training.model.entity.CompanyGroup;
import vn.elca.training.model.exception.GroupNotFoundException;

import java.util.List;

public interface CompanyGroupService {
    CompanyGroup findById(Long id) throws GroupNotFoundException;

    List<Long> getAllGroups();
}
