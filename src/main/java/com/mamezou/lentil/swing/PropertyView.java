package com.mamezou.lentil.swing ;

import java.awt.Color ;
import java.awt.Component ;

import javax.swing.JTabbedPane ;
import javax.swing.JTable ;
import javax.swing.border.TitledBorder ;
import javax.swing.table.DefaultTableCellRenderer ;
import javax.swing.table.TableModel ;

import org.springframework.context.ConfigurableApplicationContext ;

/**
 * プロパティ表示/編集用ビューのための抽象クラス。
 *
 * @param <M> 表示/編集対象となるモデルの型。
 * @author kitsune
 */
public abstract class PropertyView< M > extends JTabbedPane {

    // @Category constant definitions

    /**
     * プロパティ名のカラム・インデックス。
     */
    protected static final int COLUMN_INDEX_FOR_NAME = 0 ;

    /**
     * プロパティ値のカラム・インデックス。
     */
    protected static final int COLUMN_INDEX_FOR_VALUE = 1 ;

    /**
     * プロパティ名カラムのデフォルト幅。単位はピクセル。
     */
    protected static final int DEFAULT_PROPERTY_COLUMN_WIDTH = 64 ;

    /**
     * プロパティ値カラムのデフォルト幅。単位はピクセル。
     */
    protected static final int DEFAULT_VALUE_COLUMN_WIDTH = 256 ;

    /**
     * プロパティ名セルの背景色。
     */
    protected static final Color PROPERTY_NAME_CELL_BACKGROUND_COLOR = new Color( 222 , 222 , 222 ) ;

    // @Category class variables

    /**
     * プロパティ名カラムのセル・レンダラ。
     */
    private static DefaultTableCellRenderer PropertyNameColumnCellRenderer = null ;

    // @Category instance variables

    /**
     * Spring Framework のアプリケーション・コンテキスト。
     */
    protected ConfigurableApplicationContext context ;

    /**
     * 表示/編集の対象となっているモデル。
     */
    protected M targetModel ;

    // @Category instance creation

    /**
     * コンストラクタ。
     *
     * @param context Spring Framework のアプリケーション・コンテキスト。
     * @throws IllegalArgumentException {@code context} として {@code null} が渡された場合。
     */
    protected PropertyView( final ConfigurableApplicationContext context ) throws IllegalArgumentException {
        super() ;
        if ( null == context ) {
            throw new IllegalArgumentException( "context should not be null." ) ; //$NON-NLS-1$
        }
        this.context = context ;
        this.initializeComponents() ;
        this.updateAll() ;
    }

    // @Category initilize-release

    /**
     * 指定された {@link TableModel} をモデルとする新しいプロパティ・テーブルを生成して返す。
     *
     * @param tableModel 指定されたテーブル・モデル。
     * @return 新たに生成されたプロパティ・テーブル。
     */
    protected JTable createPropertyTableView( final TableModel tableModel ) {
        final JTable tableView = new JTable( tableModel ) ;
        tableView.setAutoResizeMode( JTable.AUTO_RESIZE_OFF ) ;
        tableView.getColumnModel().getColumn( COLUMN_INDEX_FOR_NAME ).setPreferredWidth( DEFAULT_PROPERTY_COLUMN_WIDTH ) ;
        tableView.getColumnModel().getColumn( COLUMN_INDEX_FOR_VALUE ).setPreferredWidth( DEFAULT_VALUE_COLUMN_WIDTH ) ;
        tableView.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS ) ;
        tableView.getColumnModel().getColumn( COLUMN_INDEX_FOR_NAME ).setCellRenderer( this.getPropertyNameColumnCellRenderer() ) ;
        return tableView ;
    }

    /**
     * プロパティ名カラムのセル・レンダラを返す。
     *
     * @return プロパティ名カラムのセル・レンダラ。
     */
    protected DefaultTableCellRenderer getPropertyNameColumnCellRenderer() {
        if ( null == PropertyNameColumnCellRenderer ) {
            PropertyNameColumnCellRenderer = new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent( final JTable table , final Object value , final boolean isSelected , final boolean hasFocus , final int row , final int column ) {
                    final Component component = super.getTableCellRendererComponent( table , value , isSelected , hasFocus , row , column ) ;
                    if ( !isSelected ) {
                        component.setBackground( PROPERTY_NAME_CELL_BACKGROUND_COLOR ) ;
                    }
                    return component ;
                }

            } ;
        }
        return PropertyNameColumnCellRenderer ;
    }

    /**
     * レシーバの子 GUI 部品群を生成/初期化する。
     */
    protected void initializeComponents() {
        this.setBorder( new TitledBorder( this.getBorderTitle() ) ) ;
    }

    // @Category accessing

    /**
     * プロパティ・ビューのボーダー部分に表示されるタイトル文字列を返す。
     *
     * @return プロパティ・ビューのボーダー部分に表示されるタイトル文字列。
     */
    public abstract String getBorderTitle() ;

    /**
     * 表示/編集の対象となっているモデルを返す。
     *
     * @return 表示/編集の対象となっているモデル。表示/編集対象が無い場合は {@code null} が返される。
     */
    public M getTargetModel() {
        return this.targetModel ;
    }

    /**
     * 表示/編集の対象となるモデルを設定する。
     *
     * @param newModel 新たに表示/編集の対象となるモデル。何も表示/編集しない場合は {@code null} を渡す。
     * @throws IllegalArgumentException {@code newModel} として表示/編集できないタイプのモデルが渡された場合。
     */
    public void setTargetModel( final M newModel ) throws IllegalArgumentException {
        if ( this.targetModel != newModel ) {
            this.targetModel = newModel ;
            this.updateAll() ;
        }
    }

    // @Category updating

    /**
     * 現在の {@link #model} の内容に合致するようにレシーバが保持しているすべてのフィールドの表示を更新する。
     */
    protected void updateAll() {}

}
