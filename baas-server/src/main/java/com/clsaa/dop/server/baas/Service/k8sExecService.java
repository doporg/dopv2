package com.clsaa.dop.server.baas.Service;

import com.google.common.io.ByteStreams;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Exec;
import org.apache.commons.cli.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 注释写在这
 *
 * @author Monhey
 */
@Service(value = "k8sExecService")
public class k8sExecService {
    @Autowired
    k8sClientService k8sClientService;
    public String KubectlExec(String PodName,String NameSpace,String ShellCommand) throws IOException, ApiException, ParseException, InterruptedException {
        ByteArrayOutputStream baoStream = new ByteArrayOutputStream(1024*1024);
        PrintStream cacheStream = new PrintStream(baoStream);
        PrintStream oldStream = System.out;
        System.setOut(cacheStream);
        final Options options = new Options();
        k8sClientService.setK8sClient();
        options.addOption(new Option("p", "pod", true, "The name of the pod"));
        options.addOption(new Option("n", "namespace", true, "The namespace of the pod"));
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, null);
        String podName = cmd.getOptionValue("p", PodName);
        String namespace = cmd.getOptionValue("n", NameSpace);
        List<String> commands = new ArrayList<>();
        Exec exec = new Exec();
        boolean tty = System.console() != null;
        final Process proc =
                exec.exec(
                        namespace,
                        podName,
                        commands.isEmpty()
                                ? new String[] {"sh","-c",ShellCommand}
                                : commands.toArray(new String[commands.size()]),
                        true,
                        tty);

        Thread in =
                new Thread(
                        new Runnable() {
                            public void run() {
                                try {
                                    ByteStreams.copy(System.in, proc.getOutputStream());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
        in.start();
        Thread out =
                new Thread(
                        new Runnable() {
                            public void run() {
                                try {
                                    String res = IOUtils.toString(proc.getInputStream(), "utf-8");
                                    System.out.println(res);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
        out.start();
        proc.waitFor();
        out.join();
        proc.destroy();
        String res = baoStream.toString();
        System.setOut(oldStream);
        return res;
    }
}
