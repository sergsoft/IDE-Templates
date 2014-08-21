package com.arcbees.plugin.template;

import com.arcbees.plugin.template.domain.NamedOptions;

/**
 * Created by serg on 21.08.2014.
 */
public abstract class NamedCreator<TOption extends NamedOptions, TCreated> extends BaseCreator<TOption, TCreated> {

    protected NamedCreator(TOption tOption, Class<TCreated> createdClass, boolean remote) {
        super(tOption, createdClass, remote);
    }

    @Override
    protected String renderFileName(String fileName) {
        return super.renderFileName(fileName).replace("__name__", getOption().getName());
    }
}
