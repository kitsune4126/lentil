/* MainWindow.java */
package com.mamezou.lentil.swing ;

import java.awt.BorderLayout ;
import java.awt.Component ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.event.KeyEvent ;
import java.awt.event.WindowEvent ;

import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JMenu ;
import javax.swing.JMenuBar ;
import javax.swing.JMenuItem ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JSplitPane ;
import javax.swing.JTabbedPane ;

/**
 * Swing ベース GUI のメイン・ウインドウ。
 *
 * @author kitsune
 */
public class MainWindow extends JFrame {

    // @Category instance variables

    /**
     * ウインドウ最下部のステータス・ペインに表示されるラベル。
     */
    private JLabel statusLabel ;

    // @Category instance creation

    /**
     * デフォルト・コンストラクタ。
     *
     * @throws Exception GUI 部品の初期化時に何らかの例外が発生した場合。
     */
    public MainWindow() throws Exception {
        super( Constants.getMessage( "swing.MainWindow.Title" ) ) ; //$NON-NLS-1$
        this.setSize( 640 , 480 ) ;
        this.initializeComponents() ;
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
        this.setStatusText( "Status: Initialized" ) ;
    }

    // @Category initialize-release

    /**
     * レシーバの子 GUI 部品群を生成/初期化する。
     *
     * @throws Exception GUI 部品の初期化時に何らかの例外が発生した場合。
     */
    protected void initializeComponents() throws Exception {
        this.setJMenuBar( this.createMenuBar() ) ;
        final JTabbedPane centerPane = this.createCenterPane() ;
        final JTabbedPane westPane = this.createWestPane() ;
        final JTabbedPane eastPane = this.createEastPane() ;
        final JTabbedPane southPane = this.createSouthPane() ;
        final JSplitPane splitPane0 = new JSplitPane( JSplitPane.VERTICAL_SPLIT , false ) ;
        splitPane0.setOneTouchExpandable( true ) ;
        splitPane0.setBottomComponent( southPane ) ;
        final JSplitPane splitPane1 = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT , false ) ;
        splitPane1.setOneTouchExpandable( true ) ;
        final JSplitPane splitPane2 = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT , false ) ;
        splitPane2.setOneTouchExpandable( true ) ;
        splitPane2.setLeftComponent( westPane ) ;
        splitPane2.setRightComponent( centerPane ) ;
        splitPane1.setLeftComponent( splitPane2 ) ;
        splitPane1.setRightComponent( eastPane ) ;
        splitPane0.setTopComponent( splitPane1 ) ;
        this.add( splitPane0 , BorderLayout.CENTER ) ;
        splitPane0.setDividerLocation( 320 ) ;
        splitPane2.setDividerLocation( 160 ) ;
        splitPane1.setDividerLocation( 480 ) ;
        final Component statusPane = this.createStatusPane() ;
        this.add( statusPane , BorderLayout.SOUTH ) ;
    }

    /**
     * レシーバの中央ペインを生成して返す。
     *
     * @return 生成されたペイン。
     * @throws Exception ペインの生成時に何らかの例外が発生した場合。
     */
    protected JTabbedPane createCenterPane() throws Exception {
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
    protected JTabbedPane createEastPane() throws Exception {
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
    protected JMenu createFileMenu() throws Exception {
        final JMenu menu = new JMenu( Constants.getMessage( "swing.MainWindow.MenuBar.File" ) ) ; //$NON-NLS-1$
        menu.setMnemonic( KeyEvent.VK_F ) ;
        final JMenuItem itemExit = new JMenuItem( Constants.getMessage( "swing.MainWindow.MenuBar.File.Exit" ) , KeyEvent.VK_X ) ; //$NON-NLS-1$
        itemExit.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                doFileExitMenu( e ) ;
            }

        } ) ;
        menu.add( itemExit ) ;
        return menu ;
    }

    /**
     * レシーバの「ヘルプ」メニューを生成して返す。
     *
     * @return 生成されたメニュー。
     * @throws Exception メニューの生成時に何らかの例外が発生した場合。
     */
    protected JMenu createHelpMenu() throws Exception {
        final JMenu menu = new JMenu( Constants.getMessage( "swing.MainWindow.MenuBar.Help" ) ) ; //$NON-NLS-1$
        menu.setMnemonic( KeyEvent.VK_H ) ;
        final JMenuItem itemAbout = new JMenuItem( Constants.getMessage( "swing.MainWindow.MenuBar.Help.About" ) , KeyEvent.VK_A ) ; //$NON-NLS-1$
        itemAbout.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                doHelpAboutMenu( e ) ;
            }

        } ) ;
        menu.add( itemAbout ) ;
        return menu ;
    }

    /**
     * レシーバのメニュー・バーを生成して返す。
     *
     * @return 生成されたメニュー・バー。
     * @throws Exception メニュー・バーの生成時に何らかの例外が発生した場合。
     */
    protected JMenuBar createMenuBar() throws Exception {
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
    protected JTabbedPane createSouthPane() throws Exception {
        // FIXME 未実装
        final JTabbedPane pane = new JTabbedPane() ;
        return pane ;
    }

    /**
     * レシーバの最下部に配置されるステータス表示用ペインを生成して返す。
     *
     * @return 生成されたペイン。
     * @throws Exception ペインの生成時に何らかの例外が発生した場合。
     */
    protected JPanel createStatusPane() throws Exception {
        // FIXME 未実装
        final JPanel pane = new JPanel() ;
        this.statusLabel = new JLabel( " " ) ;
        pane.add( this.statusLabel ) ;
        return pane ;
    }

    /**
     * レシーバの西側ペインを生成して返す。
     *
     * @return 生成されたペイン。
     * @throws Exception ペインの生成時に何らかの例外が発生した場合。
     */
    protected JTabbedPane createWestPane() throws Exception {
        // FIXME 未実装
        final JTabbedPane pane = new JTabbedPane() ;
        final ModelRepositoryExplorerView explorerView = new ModelRepositoryExplorerView() ;
        pane.add( Constants.getMessage( "swing.ModelRepositoryExplorerView.TabTile" ) , new JScrollPane( explorerView ) ) ; //$NON-NLS-1$
        return pane ;
    }

    // @Category accessing

    /**
     * ステータス部の表示文字列を指定された文字列に変更する。
     *
     * @param newText 指定された文字列。{@code null} が渡された場合はブランク一文字 {@code " "} が指定されたものとみなされる。
     */
    public void setStatusText( final String newText ) {
        this.statusLabel.setText( ( null == newText ) ? " " : newText ) ;
    }

    // @Category event handling

    /**
     * 「File>Exit」メニューのアクション・ハンドラ。
     *
     * @param e メニュー・アイテムが選択された時のイベント。
     */
    protected void doFileExitMenu( final ActionEvent e ) {
        this.dispatchEvent( new WindowEvent( this , WindowEvent.WINDOW_CLOSING ) ) ;
    }

    /**
     * 「Help>About」メニューのアクション・ハンドラ。
     *
     * @param e メニュー・アイテムが選択された時のイベント。
     */
    protected void doHelpAboutMenu( final ActionEvent e ) {
        // FIXME 未実装
        System.out.println( "Help>About menu is selected." ) ;
    }

    // @Category utility

    /**
     * 新しいメイン・ウインドウを生成して開く。
     *
     * @param args コマンド・ラインで指定されたパラメータ文字列の配列。
     * @throws Exception アプリケーションの初期化やウインドウ・オープン時に何らかの例外が発生した場合。
     */
    public static void open( String ... args ) throws Exception {
        new MainWindow().setVisible( true ) ;
    }

}
