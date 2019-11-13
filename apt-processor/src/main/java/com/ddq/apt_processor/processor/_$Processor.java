package com.ddq.apt_processor.processor;

import com.ddq.apt_annotation.Bind;
import com.ddq.apt_processor.factory._$CreateFactory;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;


/**
 * Author : ddq
 * Time : 2019/11/12 11:53
 * Description :
 */
@AutoService(Processor.class)
public class _$Processor extends AbstractProcessor {

    private Elements es;
    private ProcessingEnvironment pe;
    private Map<String, _$CreateFactory> factoryMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        es = processingEnv.getElementUtils();
        pe = processingEnv;
    }



    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> s = new HashSet<>();
        s.add(Bind.class.getCanonicalName());
        return s;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        factoryMap.clear();
        Set<? extends Element> temp  = roundEnv.getElementsAnnotatedWith(Bind.class);
        for (Element e : temp) {
            VariableElement ve = (VariableElement) e;
            TypeElement te = (TypeElement) ve.getEnclosingElement();
            String fullName = te.getQualifiedName().toString();
            _$CreateFactory tempFactory = factoryMap.get(fullName);
            if (tempFactory == null) {
                tempFactory = new _$CreateFactory(es,te);
                factoryMap.put(fullName, tempFactory);
            }
            Bind bindAnnotation = ve.getAnnotation(Bind.class);
            int id = bindAnnotation.value();
            tempFactory.putElement(id,ve);
        }
        for (String key : factoryMap.keySet()) {
            _$CreateFactory cf = factoryMap.get(key);
            JavaFile jf = JavaFile.builder(cf.getPackageName(),cf.generateClassCodeWithJavapoet())
                    .build();
            try {
                jf.writeTo(pe.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
