package com.jpdacunha.media.batch.videotransfer.test.ftp;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

import com.jpdacunha.media.batch.videotransfer.ftp.FtpClient;

public class FtpClientIntegrationTest {
	
	private FakeFtpServer fakeFtpServer;
	 
    private FtpClient ftpClient;
 
    @Before
    public void setup() throws IOException {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "/data"));
 
        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data"));
        fileSystem.add(new FileEntry("/data/foobar.txt", "abcdef 1234567890"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);
 
        fakeFtpServer.start();
 
        ftpClient = new FtpClient("localhost", fakeFtpServer.getServerControlPort(), "user", "password");
        ftpClient.open();
    }
    
    @Test
    public void givenRemoteFile_whenListingRemoteFiles_thenItIsContainedInList() throws IOException {
        Collection<String> files = ftpClient.listFiles("");
        boolean contains = files.contains("foobar.txt");
        assertTrue(contains);  
    }
    
    @Test
    public void givenRemoteFile_whenDownloading_thenItIsOnTheLocalFilesystem() throws IOException {
        ftpClient.downloadFile("/buz.txt", "downloaded_buz.txt");
        boolean exists = new File("downloaded_buz.txt").exists();
        assertTrue(exists);
        new File("downloaded_buz.txt").delete(); // cleanup
    }
    
    @Test
    public void givenLocalFile_whenUploadingIt_thenItExistsOnRemoteLocation() throws URISyntaxException, IOException {       
        File file = new File("src/test/resources/videotransfert/FtpClientIntegrationTest/baz.txt");
        ftpClient.putFileToPath(file, "/buz.txt");
        boolean exists = fakeFtpServer.getFileSystem().exists("/buz.txt");
        assertTrue(exists);
    }
 
    @After
    public void teardown() throws IOException {
        ftpClient.close();
        fakeFtpServer.stop();
    }
    
}
