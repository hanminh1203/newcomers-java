package vn.elca.training.repository.custom;

import vn.elca.training.model.entity.CompanyGroup;

public interface CompanyGroupRepositoryCustom {
    CompanyGroup findByLeaderName(String leaderName);
}
