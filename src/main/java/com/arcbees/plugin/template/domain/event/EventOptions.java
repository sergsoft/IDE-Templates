package com.arcbees.plugin.template.domain.event;

import com.arcbees.plugin.template.domain.NamedOptions;
import com.arcbees.plugin.template.domain.ParameterOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by serg on 21.08.2014.
 */
public class EventOptions extends NamedOptions {

    private String packageName;

    private List<ParameterOptions> parameters = new ArrayList<ParameterOptions>();

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<ParameterOptions> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterOptions> parameters) {
        this.parameters = parameters;
    }
}
