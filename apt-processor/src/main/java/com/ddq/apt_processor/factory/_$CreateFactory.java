package com.ddq.apt_processor.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Author : ddq
 * Time : 2019/11/12 14:04
 * Description :
 */
public class _$CreateFactory {
    private String className;
    private String packageName;
    private TypeElement te;
    private Map<Integer, VariableElement> variableElementMap = new HashMap<>();

    public _$CreateFactory(Elements es, TypeElement te) {
        this.te = te;

        PackageElement pe = es.getPackageOf(te);
        this.packageName = pe.getQualifiedName().toString();
        this.className = te.getSimpleName().toString() + "_AutoGenerate";
    }

    public void putElement(int id, VariableElement ve) {
        variableElementMap.put(id, ve);
    }

    public TypeSpec generateClassCodeWithJavapoet() {
        TypeSpec ts = TypeSpec.classBuilder(className)
                .addMethod(generateMethodCodeWithJavapoet())
                .addModifiers(Modifier.PUBLIC)
                .build();
        return ts;
    }

    private MethodSpec generateMethodCodeWithJavapoet(){
        ClassName cn = ClassName.bestGuess(te.getQualifiedName().toString());
        MethodSpec.Builder ms = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(cn,"owner")
                .returns(void.class);
        for (int id : variableElementMap.keySet()) {
            VariableElement ve = variableElementMap.get(id);
            String viewName = ve.getSimpleName().toString();
            String viewType = ve.asType().toString();
            ms.addCode("owner."+viewName+" = "+"("+viewType+")"
                    +"(((androidx.appcompat.app.AppCompatActivity)owner).findViewById("
                    +id+"));"
            );
        }
        return ms.build();
    }

    public String getPackageName(){
        return packageName;
    }
}
