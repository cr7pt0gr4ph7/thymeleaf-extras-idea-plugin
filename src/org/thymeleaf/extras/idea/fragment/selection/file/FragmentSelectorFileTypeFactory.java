package org.thymeleaf.extras.idea.fragment.selection.file;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class FragmentSelectorFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(FragmentSelectorFileType.INSTANCE, FragmentSelectorFileType.DEFAULT_EXTENSION);
    }
}
