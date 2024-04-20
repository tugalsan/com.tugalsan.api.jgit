module com.tugalsan.api.jgit {
    requires org.eclipse.jgit;
    requires com.tugalsan.api.union;
    requires com.tugalsan.api.list;
    requires com.tugalsan.api.url;
    requires com.tugalsan.api.runnable;
    exports com.tugalsan.api.jgit.server;
}
