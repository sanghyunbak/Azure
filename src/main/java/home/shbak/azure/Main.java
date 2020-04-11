package home.shbak.azure;

import com.microsoft.azure.storage.blob.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.security.InvalidKeyException;

public class Main {
    public static void main(String[] args) throws InvalidKeyException, IOException {
        // Retrieve the credentials and initialize SharedKeyCredentials
//        String accountName = System.getenv("AZURE_STORAGE_ACCOUNT");
//        String accountKey = System.getenv("AZURE_STORAGE_ACCESS_KEY");
        String accountName = "sanghyunbak@gmail.com";
        String accountKey = System.getenv("AZURE_STORAGE_ACCESS_KEY");

        // Create a BlockBlobURL to run operations on Block Blobs. Alternatively create a ServiceURL, or ContainerURL for operations on Blob service, and Blob containers
        SharedKeyCredentials creds = new SharedKeyCredentials(accountName, accountKey);

        // We are using a default pipeline here, you can learn more about it at https://github.com/Azure/azure-storage-java/wiki/Azure-Storage-Java-V10-Overview
        final BlockBlobURL blobURL = new BlockBlobURL(
                new URL("https://" + accountName + ".blob.core.windows.net/mycontainer/myimage.jpg"),
                StorageURL.createPipeline(creds, new PipelineOptions())
        );

        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("myimage.jpg"));
        TransferManager.uploadFileToBlockBlob(fileChannel, blobURL,0, null).blockingGet();
    }
}
