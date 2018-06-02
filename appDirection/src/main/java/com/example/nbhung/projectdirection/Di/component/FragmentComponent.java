package com.example.nbhung.projectdirection.Di.component;


import com.example.nbhung.projectdirection.Di.module.ActivityModule;
import com.example.nbhung.projectdirection.Di.module.NetWorkModule;
import com.example.nbhung.projectdirection.Ui.main.FragmentDirector;

import dagger.Component;


@Component( modules = {NetWorkModule.class, ActivityModule.class})
public interface FragmentComponent {
    FragmentDirector inject(FragmentDirector fragmentDirector);
}
