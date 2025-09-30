package com.tugalsan.api.jgit.server;

import module com.tugalsan.api.function;
import module com.tugalsan.api.list;
import module com.tugalsan.api.union;
import module com.tugalsan.api.url;
import module org.eclipse.jgit;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import java.io.*;
import java.nio.file.*;
import java.util.List;

//https://github.com/centic9/jgit-cookbook/tree/master
//https://www.baeldung.com/jgit
public class TS_JGitUtils {

    public static TGS_UnionExcuseVoid repoCreate(Path at, TGS_FuncMTU_In1<Git> git) {
        try (var _git = Git.init().setDirectory(at.toFile()).call()) {
            git.run(_git);
            return TGS_UnionExcuseVoid.ofVoid();
        } catch (GitAPIException ex) {
            return TGS_UnionExcuseVoid.ofExcuse(ex);
        }
    }

    public static TGS_UnionExcuseVoid repoClone(TGS_Url from, Path to, TGS_FuncMTU_In1<Git> git) {
        try (var _git = Git.cloneRepository().setURI(from.toString()).setDirectory(to.toFile()).call()) {
            git.run(_git);
            return TGS_UnionExcuseVoid.ofVoid();
        } catch (GitAPIException ex) {
            return TGS_UnionExcuseVoid.ofExcuse(ex);
        }
    }

    public static TGS_UnionExcuseVoid repoOpen(Path at, TGS_FuncMTU_In1<Git> git) {
        try (var _git = Git.open(at.toFile())) {
            git.run(_git);
            return TGS_UnionExcuseVoid.ofVoid();
        } catch (IOException ex) {
            return TGS_UnionExcuseVoid.ofExcuse(ex);
        }
    }

    public static TGS_UnionExcuse<List<Branch>> listBranchNames(Path at) {
        List<Branch> branches = TGS_ListUtils.of();
        var wrap = new Object() {
            GitAPIException e = null;
        };
        var u_open = repoOpen(at, git -> {
            try {
                git.branchList().setListMode(ListMode.ALL).call().forEach(ref -> {
                    branches.add(new Branch(ref));
                });
            } catch (GitAPIException ex) {
                wrap.e = ex;
            }
        });
        if (wrap.e != null) {
            TGS_UnionExcuse.ofExcuse(wrap.e);
        }
        if (u_open.isExcuse()) {
            return u_open.toExcuse();
        }
        return TGS_UnionExcuse.of(branches);
    }

    public static record Branch(Ref ref, String name, String objectId) {

        public Branch(Ref ref) {
            this(ref, ref.getName(), ref.getObjectId().getName());
        }
    }
}
