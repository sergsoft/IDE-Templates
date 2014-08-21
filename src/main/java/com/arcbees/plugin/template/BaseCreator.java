package com.arcbees.plugin.template;

import com.arcbees.plugin.template.create.event.CreateEvent;
import com.arcbees.plugin.template.domain.presenter.RenderedTemplate;
import com.arcbees.plugin.template.utils.VelocityUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.JarResourceLoader;

import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by serg on 21.08.2014.
 */
public abstract class BaseCreator<TOption, TCreated> {

    private VelocityEngine velocityEngine;
    private boolean remote;

    private TOption option;
    private TCreated created;

    protected Logger logger;

    private Class<TCreated> createdClass;

    protected BaseCreator(TOption option, Class<TCreated> createdClass, boolean remote) {
        this.createdClass = createdClass;
        logger = Logger.getLogger(getClass().getName());

        this.option = option;
        this.remote = remote;
    }

    protected void setupVelocityLocal() {
        velocityEngine = new VelocityEngine();

        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "class, jar");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.setProperty("jar.resource.loader.class", JarResourceLoader.class.getName());
        String jarPath = CreateEvent.class.getClassLoader().getResource("templates/event/").toString();
        velocityEngine.setProperty("jar.resource.loader.path", jarPath);

        try {
            velocityEngine.init();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Velocity Init Error Local", e);
            e.printStackTrace();
        }
    }

    protected void setupVelocityRemote() throws Exception {
    	try {
            velocityEngine = VelocityUtils.createRemoveVelocityEngine(getRemoveTemplatePath());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Velocity Init Error", e);
            e.printStackTrace();
            throw e;
        }
    }

    protected VelocityContext getBaseVelocityContext() {
        VelocityContext context = new VelocityContext();
        doSetupVelocityContext(context);
        return context;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public boolean isRemote() {
        return remote;
    }

    public Template getTemplate(String templatePath){
        if (remote)
            return velocityEngine.getTemplate(templatePath);

        return velocityEngine.getTemplate(getLocalTemplatePath() + templatePath);
    }

    public void run() throws Exception {
        created = createdClass.newInstance();

        if (isRemote()) {
            setupVelocityRemote();
        } else {
            setupVelocityLocal();
        }

        doRun();
    }

    public TOption getOption() {
        return option;
    }

    public TCreated getCreated() {
        return created;
    }

    protected RenderedTemplate processTemplate(String fileName) throws Exception {
        Template template = getTemplate(fileName);
        VelocityContext context = getBaseVelocityContext();
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        RenderedTemplate rendered = new RenderedTemplate(renderFileName(fileName), writer.toString());
        return rendered;
    }

    protected abstract String getLocalTemplatePath();

    protected abstract String getRemoveTemplatePath();

    protected abstract void doSetupVelocityContext(VelocityContext context);

    protected abstract void doRun() throws Exception;

    protected String renderFileName(String fileName){
        return fileName.replace(".vm", "");
    }
}
