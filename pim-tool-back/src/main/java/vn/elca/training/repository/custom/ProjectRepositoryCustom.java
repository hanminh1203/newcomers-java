package vn.elca.training.repository.custom;

import vn.elca.training.model.entity.Project;

import java.util.List;

public interface ProjectRepositoryCustom {
    List<Project> findProjectByName(String keyWord);
    public Project findOneProjectByName(String keyWord);
}
