package org.thymeleaf.extras.idea.facet;

import com.intellij.facet.ui.FacetBasedFrameworkSupportProvider;
import com.intellij.framework.library.DownloadableLibraryService;
import com.intellij.framework.library.FrameworkSupportWithLibrary;
import com.intellij.ide.util.frameworkSupport.FrameworkSupportConfigurableBase;
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel;
import com.intellij.ide.util.frameworkSupport.FrameworkSupportProviderBase;
import com.intellij.ide.util.frameworkSupport.FrameworkVersion;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ui.configuration.ModulesConfigurator;
import com.intellij.openapi.roots.ui.configuration.libraries.CustomLibraryDescription;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.event.HyperlinkEvent;
import java.util.List;

/**
 * "Add Framework" support.
 */
public class ThymeleafFrameworkSupportProvider extends FacetBasedFrameworkSupportProvider<ThymeleafFacet> {

    private static final Logger LOG = Logger.getInstance("#org.thymeleaf.extras.idea.facet.ThymeleafFrameworkSupportProvider");

    public ThymeleafFrameworkSupportProvider() {
        super(ThymeleafFacetType.getInstance());
    }

    @Override
    public String getTitle() {
        return UIUtil.replaceMnemonicAmpersand("&Thymeleaf");
    }

    @NotNull
    @Override
    public FrameworkSupportConfigurableBase createConfigurable(@NotNull final FrameworkSupportModel model) {
        return new ThymeleafFrameworkSupportConfigurable(this, model, getVersions(), getVersionLabelText());
    }

    @Override
    protected void setupConfiguration(final ThymeleafFacet facet,
                                      final ModifiableRootModel rootModel, final FrameworkVersion version) {
    }

    @Override
    protected void onFacetCreated(final ThymeleafFacet facet,
                                  final ModifiableRootModel rootModel, final FrameworkVersion version) {
        final Module module = facet.getModule();
        StartupManager.getInstance(module.getProject()).runWhenProjectIsInitialized(new Runnable() {
            public void run() {
                final VirtualFile[] sourceRoots = ModuleRootManager.getInstance(module).getSourceRoots();
                if (sourceRoots.length <= 0) {
                    return;
                }

                // TODO Integrate with Spring (e.g. insert a bean declaration?)
                // final PsiDirectory directory = PsiManager.getInstance(module.getProject()).findDirectory(sourceRoots[0]);
                // if (directory == null ||
                //         directory.findFile(ThymeleafConstants.THYMELEAF_XML_DEFAULT_FILENAME) != null) {
                //     return;
                // }

                // final ThymeleafFileTemplateProvider templateProvider = new ThymeleafFileTemplateProvider(module);
                // final FileTemplate thymeleafXmlTemplate = templateProvider.determineFileTemplate();

                try {
                    final ThymeleafFacetConfiguration thymeleafFacetConfiguration = facet.getConfiguration();

                    // // create empty thymeleaf.xml & fileset with all found thymeleaf-*.xml files (thymeleaf2.jar, plugins)
                    // final PsiElement psiElement = FileTemplateUtil.createFromTemplate(thymeleafXmlTemplate,
                    //         ThymeleafConstants.THYMELEAF_XML_DEFAULT_FILENAME,
                    //         null,
                    //         directory);
                    // final Set<ThymeleafFileSet> empty = Collections.emptySet();
                    // final ThymeleafFileSet fileSet = new ThymeleafFileSet(ThymeleafFileSet.getUniqueId(empty),
                    //         ThymeleafFileSet.getUniqueName("Default File Set", empty),
                    //         thymeleafFacetConfiguration);
                    // fileSet.addFile(((XmlFile) psiElement).getVirtualFile());
                    //
                    // final ThymeleafConfigsSearcher searcher = new ThymeleafConfigsSearcher(module);
                    // searcher.search();
                    // final MultiMap<VirtualFile, PsiFile> jarConfigFiles = searcher.getJars();
                    // for (final VirtualFile virtualFile : jarConfigFiles.keySet()) {
                    //     final Collection<PsiFile> psiFiles = jarConfigFiles.get(virtualFile);
                    //     for (final PsiFile psiFile : psiFiles) {
                    //         fileSet.addFile(psiFile.getVirtualFile());
                    //     }
                    // }
                    // thymeleafFacetConfiguration.getFileSets().add(fileSet);
                    //
                    //
                    // // create filter & mapping in web.xml (if present)
                    // new WriteCommandAction.Simple(modifiableRootModel.getProject()) {
                    //     protected void run() throws Throwable {
                    //         final WebFacet webFacet = thymeleafFacet.getWebFacet();
                    //
                    //         final ConfigFile configFile = webFacet.getWebXmlDescriptor();
                    //         if (configFile == null) return;
                    //
                    //         final XmlFile webXmlFile = configFile.getXmlFile();
                    //         final WebApp webApp = JamCommonUtil.getRootElement(webXmlFile, WebApp.class, null);
                    //         if (webApp == null) return;
                    //         if (!FileModificationService.getInstance().prepareFileForWrite(webXmlFile)) return;
                    //
                    //         final Filter thymeleafFilter = webApp.addFilter();
                    //         thymeleafFilter.getFilterName().setStringValue("thymeleaf2");
                    //
                    //         @NonNls final String filterClass = templateProvider.is21orNewer() ?
                    //                 ThymeleafConstants.THYMELEAF_2_1_FILTER_CLASS :
                    //                 ThymeleafConstants.THYMELEAF_2_0_FILTER_CLASS;
                    //         thymeleafFilter.getFilterClass().setStringValue(filterClass);
                    //
                    //         final FilterMapping filterMapping = webApp.addFilterMapping();
                    //         filterMapping.getFilterName().setValue(thymeleafFilter);
                    //         filterMapping.addUrlPattern().setStringValue("/*");
                    //     }
                    // }.execute();


                    //
                    // Show a notification with a clickable hyperlink which leads
                    // to the configuration dialog of the newly created facet
                    //

                    final NotificationListener showFacetSettingsListener = new NotificationListener() {
                        @Override
                        public void hyperlinkUpdate(@NotNull final Notification notification,
                                                    @NotNull final HyperlinkEvent event) {
                            if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                                notification.expire();
                                ModulesConfigurator.showFacetSettingsDialog(facet, null);
                            }
                        }
                    };

                    Notifications.Bus.notify(
                            new Notification("Thymeleaf", "Thymeleaf Setup",
                                    "Thymeleaf Facet has been created, please check <a href=\"more\">created fileset</a>",
                                    NotificationType.INFORMATION,
                                    showFacetSettingsListener),
                            module.getProject());

                } catch (Exception e) {
                    LOG.error("error creating thymeleaf.xml from template", e);
                }
            }
        });
    }

    private static class ThymeleafFrameworkSupportConfigurable extends FrameworkSupportConfigurableBase implements FrameworkSupportWithLibrary {
        private ThymeleafFrameworkSupportConfigurable(final FrameworkSupportProviderBase frameworkSupportProvider,
                                                      final FrameworkSupportModel model,
                                                      @NotNull final List<FrameworkVersion> versions,
                                                      @Nullable final String versionLabelText) {
            super(frameworkSupportProvider, model, versions, versionLabelText);
        }

        @Nullable
        @Override
        public CustomLibraryDescription createLibraryDescription() {
            return DownloadableLibraryService.getInstance().createDescriptionForType(ThymeleafLibraryType.class);
        }

        @Override
        public boolean isLibraryOnly() {
            return false;
        }
    }
}
