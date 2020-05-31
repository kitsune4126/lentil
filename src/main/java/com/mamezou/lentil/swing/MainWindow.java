package com.mamezou.lentil.swing ;

import java.awt.BorderLayout ;
import java.awt.CardLayout ;
import java.awt.Component ;
import java.awt.GridLayout ;
import java.awt.event.ActionEvent ;
import java.awt.event.KeyEvent ;
import java.awt.event.WindowEvent ;
import java.io.File ;
import java.util.Locale ;

import javax.swing.AbstractAction ;
import javax.swing.Action ;
import javax.swing.ActionMap ;
import javax.swing.JFileChooser ;
import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JMenu ;
import javax.swing.JMenuBar ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JSplitPane ;
import javax.swing.JTabbedPane ;
import javax.swing.SwingConstants ;
import javax.swing.event.TreeExpansionEvent ;
import javax.swing.event.TreeSelectionEvent ;
import javax.swing.event.TreeSelectionListener ;
import javax.swing.event.TreeWillExpandListener ;
import javax.swing.tree.ExpandVetoException ;

import org.springframework.context.ConfigurableApplicationContext ;

import com.mamezou.lentil.model.FileBasedModelRepository ;

/**
 * Swing ベース GUI のメイン・ウインドウ。
 *
 * @author kitsune
 */
public class MainWindow extends JFrame {

    // @Category constant definitions

    /**
     * 「File>Exit」のアクション名。
     */
    private static final String ACTION_KEY_FILE_EXIT = "swing.MainWindow.Action.File.Exit" ;

    /**
     * 「File>NewRepository」のアクション名。
     */
    private static final String ACTION_KEY_FILE_NEW_REPOSITORY = "swing.MainWindow.Action.File.NewRepository" ;

    /**
     * 「File>OpenRepository」のアクション名。
     */
    private static final String ACTION_KEY_FILE_OPEN_REPOSITORY = "swing.MainWindow.Action.File.OpenRepository" ;

    /**
     * 「Help>About」のアクション名。
     */
    private static final String ACTION_KEY_HELP_ABOUT = "swing.MainWindow.Action.Help.About" ;

    /**
     * デフォルトの高さ。単位はピクセル。
     */
    private static final int DEFAULT_HEIGHT = 768 ;

    /**
     * デフォルトの幅。単位はピクセル。
     */
    private static final int DEFAULT_WIDTH = 1024 ;

    /**
     * プロパティ・パネルの切り替え用に使われるプロパティ・ビューの名称：なし。
     */
    private static final String PROPERTY_VIEW_NAME_NONE = "NONE" ;

    // @Category instance variables

    /**
     * アクション名とアクションのマップ。
     */
    private ActionMap actionMap = new ActionMap() ;

    /**
     * Spring Framework のアプリケーション・コンテキスト。
     */
    private ConfigurableApplicationContext context ;

    /**
     * モデル・リポジトリ・エクスプローラー(画面左側のツリー・ビュー)。
     */
    private ModelRepositoryExplorerView explorerView ;

    /**
     * モデル要素のプロパティ表示/編集に使われる左側のパネル。
     */
    private JPanel primaryPropertyPanel ;

    /**
     * リポジトリのオープンや新規作成の際に使われるファイル・ダイアログ。
     */
    private JFileChooser repositoryDirectoryChooser ;

    /**
     * モデル要素のプロパティ表示/編集に使われる右側のパネル。
     */
    private JPanel secondaryPropertyPanel ;

    /**
     * ウインドウ最下部のステータス・ペインに表示されるラベル。
     */
    private JLabel statusLabel ;

    // @Category instance creation

    /**
     * コンストラクタ。
     *
     * @throws Exception GUI 部品の初期化時に何らかの例外が発生した場合。
     * @throws IllegalArgumentException {@code context} として {@code null} が渡された場合。
     */
    public MainWindow( final ConfigurableApplicationContext context ) throws Exception {
        super() ;
        if ( null == context ) {
            throw new IllegalArgumentException( "context should not be null." ) ; //$NON-NLS-1$
        }
        this.context = context ;
        this.setSize( DEFAULT_WIDTH , DEFAULT_HEIGHT ) ;
        this.initializeComponents() ;
        this.setTitle( this.context.getMessage( "swing.MainWindow.Title" , null , Locale.getDefault() ) ) ; //$NON-NLS-1$
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
        this.setStatusText( "Status: Initialized" ) ;
    }

    // @Category initialize-release

    /**
     * レシーバの子 GUI 部品群を生成/初期化する。
     *
     * @throws Exception GUI 部品の初期化時に何らかの例外が発生した場合。
     */
    private void initializeComponents() throws Exception {
        this.setJMenuBar( this.createMenuBar() ) ;
        final Component centerPane = this.createCenterPane() ;
        final Component westPane = this.createWestPane() ;
        final Component eastPane = this.createEastPane() ;
        final Component southPane = this.createSouthPane() ;
        final JSplitPane splitPane0 = new JSplitPane( JSplitPane.VERTICAL_SPLIT , false ) ;
        splitPane0.setResizeWeight( 0.5 ) ;
        splitPane0.setOneTouchExpandable( true ) ;
        splitPane0.setBottomComponent( southPane ) ;
        final JSplitPane splitPane1 = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT , false ) ;
        splitPane1.setResizeWeight( 0.5 ) ;
        splitPane1.setOneTouchExpandable( true ) ;
        final JSplitPane splitPane2 = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT , false ) ;
        splitPane2.setResizeWeight( 0.5 ) ;
        splitPane2.setOneTouchExpandable( true ) ;
        splitPane2.setLeftComponent( westPane ) ;
        splitPane2.setRightComponent( centerPane ) ;
        splitPane1.setLeftComponent( splitPane2 ) ;
        splitPane1.setRightComponent( eastPane ) ;
        splitPane0.setTopComponent( splitPane1 ) ;
        this.add( splitPane0 , BorderLayout.CENTER ) ;
        splitPane0.setDividerLocation( DEFAULT_HEIGHT * 3 / 5 ) ;
        splitPane2.setDividerLocation( DEFAULT_WIDTH * 1 / 5 ) ;
        splitPane1.setDividerLocation( DEFAULT_WIDTH * 4 / 5 ) ;
        final Component statusPane = this.createStatusPane() ;
        this.add( statusPane , BorderLayout.SOUTH ) ;
    }

    /**
     * レシーバの中央ペインを生成して返す。
     *
     * @return 生成されたペイン。
     * @throws Exception ペインの生成時に何らかの例外が発生した場合。
     */
    private Component createCenterPane() throws Exception {
        // FIXME 未実装
        final JTabbedPane pane = new JTabbedPane() ;
        return pane ;
    }

    /**
     * レシーバの東側ペインを生成して返す。
     *
     * @return 生成されたペイン。
     * @throws Exception ペインの生成時に何らかの例外が発生した場合。
     */
    private Component createEastPane() throws Exception {
        // FIXME 未実装
        final JTabbedPane pane = new JTabbedPane() ;
        return pane ;
    }

    /**
     * レシーバの「ファイル」メニューを生成して返す。
     *
     * @return 生成されたメニュー。
     * @throws Exception メニューの生成時に何らかの例外が発生した場合。
     */
    private JMenu createFileMenu() throws Exception {
        final JMenu menu = new JMenu( this.context.getMessage( "swing.MainWindow.MenuBar.File" , null , Locale.getDefault() ) ) ; //$NON-NLS-1$
        menu.setMnemonic( KeyEvent.VK_F ) ;
        final Action actionFileNewRepository = new AbstractAction( this.context.getMessage( ACTION_KEY_FILE_NEW_REPOSITORY , null , Locale.getDefault() ) ) {

            @Override
            public void actionPerformed( ActionEvent e ) {
                handleMenuFileNewRepository( e ) ;
            }

        } ;
        this.actionMap.put( ACTION_KEY_FILE_NEW_REPOSITORY , actionFileNewRepository ) ;
        menu.add( actionFileNewRepository ).setMnemonic( KeyEvent.VK_N ) ;
        final Action actionFileOpenRepository = new AbstractAction( this.context.getMessage( ACTION_KEY_FILE_OPEN_REPOSITORY , null , Locale.getDefault() ) ) {

            @Override
            public void actionPerformed( ActionEvent e ) {
                handleMenuFileOpenRepository( e ) ;
            }

        } ;
        this.actionMap.put( ACTION_KEY_FILE_OPEN_REPOSITORY , actionFileOpenRepository ) ;
        menu.add( actionFileOpenRepository ).setMnemonic( KeyEvent.VK_O ) ;
        menu.addSeparator() ;
        final Action actionFileExit = new AbstractAction( this.context.getMessage( ACTION_KEY_FILE_EXIT , null , Locale.getDefault() ) ) {

            @Override
            public void actionPerformed( ActionEvent e ) {
                handleMenuFileExit( e ) ;
            }

        } ;
        this.actionMap.put( ACTION_KEY_FILE_EXIT , actionFileExit ) ;
        menu.add( actionFileExit ).setMnemonic( KeyEvent.VK_X ) ;
        return menu ;
    }

    /**
     * レシーバの「ヘルプ」メニューを生成して返す。
     *
     * @return 生成されたメニュー。
     * @throws Exception メニューの生成時に何らかの例外が発生した場合。
     */
    private JMenu createHelpMenu() throws Exception {
        final JMenu menu = new JMenu( this.context.getMessage( "swing.MainWindow.MenuBar.Help" , null , Locale.getDefault() ) ) ; //$NON-NLS-1$
        menu.setMnemonic( KeyEvent.VK_H ) ;
        final Action actionHelpAbout = new AbstractAction( this.context.getMessage( ACTION_KEY_HELP_ABOUT , null , Locale.getDefault() ) ) {

            @Override
            public void actionPerformed( ActionEvent e ) {
                handleMenuHelpAbout( e ) ;
            }

        } ;
        this.actionMap.put( ACTION_KEY_HELP_ABOUT , actionHelpAbout ) ;
        menu.add( actionHelpAbout ).setMnemonic( KeyEvent.VK_A ) ;
        return menu ;
    }

    /**
     * レシーバのメニュー・バーを生成して返す。
     *
     * @return 生成されたメニュー・バー。
     * @throws Exception メニュー・バーの生成時に何らかの例外が発生した場合。
     */
    private JMenuBar createMenuBar() throws Exception {
        final JMenuBar menuBar = new JMenuBar() ;
        menuBar.add( this.createFileMenu() ) ;
        menuBar.add( this.createHelpMenu() ) ;
        return menuBar ;
    }

    /**
     * レシーバの南側ペインを生成して返す。
     *
     * @return 生成されたペイン。
     * @throws Exception ペインの生成時に何らかの例外が発生した場合。
     */
    private Component createSouthPane() throws Exception {
        this.primaryPropertyPanel = new JPanel( new CardLayout() ) ;
        this.primaryPropertyPanel.add( new JPanel() , PROPERTY_VIEW_NAME_NONE ) ;
        this.secondaryPropertyPanel = new JPanel( new CardLayout() ) ;
        this.secondaryPropertyPanel.add( new JPanel() , PROPERTY_VIEW_NAME_NONE ) ;
        final JSplitPane splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT , false ) ;
        splitPane.setResizeWeight( 0.5 ) ;
        splitPane.setOneTouchExpandable( true ) ;
        splitPane.setLeftComponent( this.primaryPropertyPanel ) ;
        splitPane.setRightComponent( this.secondaryPropertyPanel ) ;
        splitPane.setDividerLocation( DEFAULT_WIDTH / 2 ) ;
        return splitPane ;
    }

    /**
     * レシーバの最下部に配置されるステータス表示用ペインを生成して返す。
     *
     * @return 生成されたペイン。
     * @throws Exception ペインの生成時に何らかの例外が発生した場合。
     */
    private JPanel createStatusPane() throws Exception {
        final JPanel pane = new JPanel( new GridLayout( 1 , 1 ) ) ;
        this.statusLabel = new JLabel( " " , SwingConstants.LEADING ) ;
        pane.add( this.statusLabel ) ;
        return pane ;
    }

    /**
     * リポジトリのオープンや新規作成の際に使われるファイル・ダイアログを生成して返す。
     *
     * @return 生成されたファイル・ダイアログ。
     */
    private JFileChooser createRepositoryDirectoryChooser() {
        final JFileChooser fileChooser = new JFileChooser() ;
        fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY ) ;
        fileChooser.setMultiSelectionEnabled( false ) ;
        fileChooser.setAcceptAllFileFilterUsed( false ) ;
        return fileChooser ;
    }

    /**
     * レシーバの西側ペインを生成して返す。
     *
     * @return 生成されたペイン。
     * @throws Exception ペインの生成時に何らかの例外が発生した場合。
     */
    private Component createWestPane() throws Exception {
        final JTabbedPane pane = new JTabbedPane() ;
        this.explorerView = new ModelRepositoryExplorerView( this.context ) ;
        this.explorerView.addTreeSelectionListener( new TreeSelectionListener() {

            @Override
            public void valueChanged( final TreeSelectionEvent event ) {
                handleTreeSelectionChanged( event ) ;
            }

        } ) ;
        this.explorerView.addTreeWillExpandListener( new TreeWillExpandListener() {

            @Override
            public void treeWillExpand( final TreeExpansionEvent event ) throws ExpandVetoException {
                handleTreeWillExpand( event ) ;
            }

            @Override
            public void treeWillCollapse( final TreeExpansionEvent event ) throws ExpandVetoException {
                handleTreeWillCollapse( event ) ;
            }

        } ) ;
        pane.add( this.context.getMessage( "swing.MainWindow.TabTitle.ModelRepositoryExplorerView" , null , Locale.getDefault() ) , new JScrollPane( this.explorerView ) ) ; //$NON-NLS-1$
        return pane ;
    }

    // @Category accessing

    /**
     * ステータス部の表示文字列を指定された文字列に変更する。
     *
     * @param newText 指定された文字列。{@code null} が渡された場合はブランク一文字 {@code " "} が指定されたものとみなされる。
     */
    private void setStatusText( final String newText ) {
        this.statusLabel.setText( ( null == newText ) ? " " : newText ) ;
    }

    // @Category event handling

    /**
     * 「File>Exit」メニューのアクション・ハンドラ。
     *
     * @param event メニュー・アイテムが選択された時のイベント。
     */
    private void handleMenuFileExit( final ActionEvent event ) {
        this.dispatchEvent( new WindowEvent( this , WindowEvent.WINDOW_CLOSING ) ) ;
    }

    /**
     * 「File>NewRepository」メニューのアクション・ハンドラ。
     *
     * @param event メニュー・アイテムが選択された時のイベント。
     */
    private void handleMenuFileNewRepository( final ActionEvent event ) {
        if ( null == this.repositoryDirectoryChooser ) {
            this.repositoryDirectoryChooser = this.createRepositoryDirectoryChooser() ;
        }
        if ( JFileChooser.APPROVE_OPTION == this.repositoryDirectoryChooser.showOpenDialog( this ) ) {
            final File selectedFile = this.repositoryDirectoryChooser.getSelectedFile() ;
            if ( ( null != selectedFile ) && ( selectedFile.canWrite() ) ) {
                this.explorerView.addRepository( new FileBasedModelRepository( selectedFile.toPath() , null ) ) ;
            }
        }
    }

    /**
     * 「File>OpenRepository」メニューのアクション・ハンドラ。
     *
     * @param event メニュー・アイテムが選択された時のイベント。
     */
    private void handleMenuFileOpenRepository( final ActionEvent event ) {
        // FIXME 未実装
        System.out.println( "Help>OpenRepository menu is selected." ) ;
    }

    /**
     * 「Help>About」メニューのアクション・ハンドラ。
     *
     * @param event メニュー・アイテムが選択された時のイベント。
     */
    private void handleMenuHelpAbout( final ActionEvent event ) {
        // FIXME 未実装
        System.out.println( "Help>About menu is selected." ) ;
    }

    /**
     * {@link #explorerView} の選択項目が変わった。
     *
     * @param event 項目が選択/選択解除された時のイベント。
     */
    private void handleTreeSelectionChanged( final TreeSelectionEvent event ) {
        // FIXME 未実装
    }

    /**
     * {@link #explorerView} のノードが折りたたまれようとしている。
     *
     * @param event ノードが折りたたまれようとしている時のイベント。
     * @throws ExpandVetoException ノードの折りたたみを禁止する時にスローされる。
     */
    private void handleTreeWillCollapse( final TreeExpansionEvent event ) throws ExpandVetoException {
        // FIXME 未実装
    }

    /**
     * {@link #explorerView} のノードが展開されようとしている。
     *
     * @param event ノードが展開されようとしている時のイベント。
     * @throws ExpandVetoException ノードの展開を禁止する時にスローされる。
     */
    private void handleTreeWillExpand( final TreeExpansionEvent event ) throws ExpandVetoException {
        // FIXME 未実装
    }

    // @Category utility

    /**
     * 新しいメイン・ウインドウを生成して開く。
     *
     * @param context Spring Framework のアプリケーション・コンテキスト。
     * @param args コマンド・ラインで指定されたパラメータ文字列の配列。
     * @throws Exception アプリケーションの初期化やウインドウ・オープン時に何らかの例外が発生した場合。
     */
    public static void open( final ConfigurableApplicationContext context , final String ... args ) throws Exception {
        new MainWindow( context ).setVisible( true ) ;
    }

}
