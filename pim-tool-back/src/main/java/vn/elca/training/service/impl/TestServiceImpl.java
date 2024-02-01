package vn.elca.training.service.impl;

import org.springframework.stereotype.Service;
import vn.elca.training.service.TestService;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public void methodA(){
        System.out.print("this is method a");
    }
}
