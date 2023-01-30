package com.performance.optimize;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.internal.api.ApplicationVariantImpl;
import com.android.build.gradle.internal.publishing.AndroidArtifacts;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.ArtifactCollection;
import org.gradle.api.artifacts.result.ResolvedArtifactResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ChecksPlugin implements Plugin<Project> {
    private static final String TASK_GROUP_NAME = "checks";
    private static final String TASK_CHECK_SO = "checkSo";
    private static final String TASK_CHECK_SDK = "checkSdk";

    @Override
    public void apply(Project project) {
        project.afterEvaluate(p -> {
            Object android = p.getExtensions().findByName("android");
            if (android instanceof AppExtension) {
                for (ApplicationVariant applicationVariant : ((AppExtension) android).getApplicationVariants()) {
                    System.out.println("ChecksPlugin: applicationVariantName = " + applicationVariant.getName());
                    createSdkTask(p, (ApplicationVariantImpl) applicationVariant);
                    createSoTask(p, (ApplicationVariantImpl) applicationVariant);
                }
            }
        });
    }

    void createSdkTask(Project project, ApplicationVariantImpl applicationVariant) {
        Task task = project.getTasks().create(TASK_CHECK_SDK + "For" + upperFirstWord(applicationVariant.getName()));
        task.setGroup(TASK_GROUP_NAME);
        task.doFirst(task1 -> {
            ArtifactCollection variantCollection = applicationVariant.getVariantData()
                    .getVariantDependencies().getArtifactCollection(
                            AndroidArtifacts.ConsumedConfigType.RUNTIME_CLASSPATH,
                            AndroidArtifacts.ArtifactScope.ALL,
                            AndroidArtifacts.ArtifactType.AAR_OR_JAR);
            processSdkArtifacts(variantCollection.getArtifacts());
        });
    }

    private void processSdkArtifacts(Set<ResolvedArtifactResult> artifacts) {
        List<String> listSdk = new ArrayList<>();
        artifacts.forEach(resolvedArtifactResult -> {
            String displayName = resolvedArtifactResult.getId().getComponentIdentifier().getDisplayName();
            File file = resolvedArtifactResult.getFile();
            String extension = getExtension(file);
            if (extension != null) {
                switch (extension) {
                    case "aar":
                    case "jar":
                        if (!listSdk.contains(displayName)) {
                            listSdk.add(displayName);
                        }
                        break;
                    default:
                }
            }
        });
        Collections.sort(listSdk);
        for (String sdk : listSdk) {
            System.out.println("sdk: " + sdk);
        }
        System.out.println("Finish: 共有sdk库：" + listSdk.size() + "个");
    }

    void createSoTask(Project project, ApplicationVariantImpl applicationVariant) {
        Task task = project.getTasks().create(TASK_CHECK_SO + "For" + upperFirstWord(applicationVariant.getName()));
        task.setGroup(TASK_GROUP_NAME);
        task.doFirst(task1 -> {
            ArtifactCollection variantCollection = applicationVariant.getVariantData()
                    .getVariantDependencies().getArtifactCollection(
                            AndroidArtifacts.ConsumedConfigType.RUNTIME_CLASSPATH,
                            AndroidArtifacts.ArtifactScope.ALL,
                            AndroidArtifacts.ArtifactType.AAR);
            String so = null;
            if (project.hasProperty("so")) {
                so = (String) project.property("so");
            }
            processSoArtifacts(variantCollection.getArtifacts(), so);
        });
    }

    private void processSoArtifacts(Set<ResolvedArtifactResult> artifacts, String soName) {
        List<String> listSdk = new ArrayList<>();
        List<String> listSo = new ArrayList<>();
        artifacts.forEach(resolvedArtifactResult -> {
            String displayName = resolvedArtifactResult.getId().getComponentIdentifier().getDisplayName();
            File file = resolvedArtifactResult.getFile();
            String extension = getExtension(file);
            if (extension != null) {
                switch (extension) {
                    case "aar":
                    case "jar":
                        processZipFile(displayName, file, listSdk, listSo, soName);
                        break;
                    default:
                }
            }
        });
        System.out.println("Finish: 共有(含so)sdk库：" + listSdk.size() + "个， 共有so文件：" + listSo.size() + "个");
    }

    private void processZipFile(String displayName, File file, List<String> listSdk, List<String> listSo, String soName) {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            StringBuilder builder = new StringBuilder();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory()) {
                    int lastIndex = entry.getName().lastIndexOf("/") + 1;
                    String name = entry.getName().substring(lastIndex);
                    if (name.endsWith(".so") && (soName == null || name.contains(soName))) {
                        String sizeStr = "";
                        if (entry.getSize() > (1024 * 1024)) {
                            sizeStr = String.format("%.2fM", (entry.getSize() / 1024f / 1024f));
                        } else {
                            sizeStr = String.format("%.2fK", (entry.getSize() / 1024f));
                        }
                        builder.append("so文件 = ").append(entry.getName()).append("   size = ").append(sizeStr).append("\n");
                        if (!listSo.contains(name)) {
                            listSo.add(name);
                        }
                    }
                }
            }

            if (builder.length() != 0) {
                listSdk.add(displayName);
                System.out.println("---------------[依赖库] SDK=" + displayName + "----------------");
                System.out.println(builder);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    private String getExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf(".");
        if (lastIndex != -1) {
            return name.substring(lastIndex + 1);
        }

        return "";
    }

    private String upperFirstWord(String word) {
        if (word != null && word.length() > 0) {
            char[] chars = word.toCharArray();
            if (Character.isLowerCase(chars[0])) {
                chars[0] -= 32;
                return new String(chars);
            }
        }
        return word;
    }
}