package com.mamezou.lentil.swing ;

import java.awt.Component ;
import java.util.Locale ;

import javax.swing.JComponent ;
import javax.swing.JScrollPane ;
import javax.swing.JTable ;
import javax.swing.SwingConstants ;
import javax.swing.table.AbstractTableModel ;
import javax.swing.table.DefaultTableCellRenderer ;

import org.springframework.context.ConfigurableApplicationContext ;

import com.mamezou.lentil.model.ModelElement ;
import com.mamezou.lentil.repository.FileBasedModelRepository ;

/**
 * {@link FileBasedModelRepository} 用の主プロパティ・ビュー。
 *
 * @author kitsune
 */
public class FileBasedModelRepositoryViewPrimary extends ElementPropertyView {

    // @Category constant definitions

    /**
     * BasePath フィールドの行インデックス。
     */
    private static final int ROW_INDEX_BASE_PATH = 1 ;

    /**
     * RepositoryName フィールドの行インデックス。
     */
    private static final int ROW_INDEX_REPOSITORY_NAME = 2 ;

    // @Category instance variables

    /**
     * 「General」タブのプロパティ・テーブルのモデル。
     */
    private AbstractTableModel tableModelGeneral ;

    // @Category instance creation

    /**
     * コンストラクタ。
     *
     * @param context Spring Framework のアプリケーション・コンテキスト。
     * @throws IllegalArgumentException {@code context} として {@code null} が渡された場合。
     */
    public FileBasedModelRepositoryViewPrimary( final ConfigurableApplicationContext context ) throws IllegalArgumentException {
        super( context ) ;
    }

    // @Category initialize-release

    @Override
    protected void initializeComponents() {
        super.initializeComponents() ;
        this.addTab( this.context.getMessage( "swing.ElementPropertyView.Tab.General" , null , Locale.getDefault() ) , this.createGeneralPane() ) ; //$NON-NLS-1$
    }

    private JComponent createGeneralPane() {
        final String[] columnNames = new String[] {
                this.context.getMessage( "swing.ElementPropertyView.ColumnTitle.Property" , null , Locale.getDefault() ) , //$NON-NLS-1$
                this.context.getMessage( "swing.ElementPropertyView.ColumnTitle.Value" , null , Locale.getDefault() ), //$NON-NLS-1$
        } ;
        final String[] propertyNames = new String[] {
                this.context.getMessage( "swing.ElementPropertyView.PropertyName.Uuid" , null , Locale.getDefault() ) , //$NON-NLS-1$
                this.context.getMessage( "swing.FileBasedModelRepositoryViewPrimary.PropertyName.BasePath" , null , Locale.getDefault() ) , //$NON-NLS-1$
                this.context.getMessage( "swing.FileBasedModelRepositoryViewPrimary.PropertyName.RepositoryName" , null , Locale.getDefault() ), //$NON-NLS-1$
        } ;
        this.tableModelGeneral = new AbstractTableModel() {

            @Override
            public int getColumnCount() {
                return columnNames.length ;
            }

            @Override
            public String getColumnName( final int column ) {
                return columnNames[ column ] ;
            };

            @Override
            public int getRowCount() {
                return propertyNames.length ;
            }

            @Override
            public Object getValueAt( final int rowIndex , final int columnIndex ) {
                if ( COLUMN_INDEX_NAME == columnIndex ) {
                    return propertyNames[ rowIndex ] ;
                } else {
                    final FileBasedModelRepository element = getModelElement() ;
                    switch ( rowIndex ) {
                    case ROW_INDEX_UUID :
                        return ( null == element ) ? null : element.getUuid() ;
                    case ROW_INDEX_BASE_PATH :
                        return ( null == element ) ? null : element.getBasePath() ;
                    case ROW_INDEX_REPOSITORY_NAME :
                        return ( null == element ) ? null : element.getName() ;
                    default :
                        return null ;
                    }
                }
            }

            @Override
            public boolean isCellEditable( final int rowIndex , final int columnIndex ) {
                return ( COLUMN_INDEX_NAME != columnIndex ) && ( null != getModelElement() ) && ( ROW_INDEX_UUID != rowIndex ) && ( ROW_INDEX_BASE_PATH != rowIndex ) ;
            };

            @Override
            public void setValueAt( final Object aValue , final int rowIndex , final int columnIndex ) {
                if ( COLUMN_INDEX_VALUE == columnIndex ) {
                    switch ( rowIndex ) {
                    case ROW_INDEX_REPOSITORY_NAME :
                        getModelElement().setName( ( (String)( aValue ) ).trim() ) ;
                    }
                }
            };

        } ;
        final JTable tableView = new JTable( this.tableModelGeneral ) ;
        tableView.setAutoResizeMode( JTable.AUTO_RESIZE_OFF ) ;
        tableView.getColumnModel().getColumn( COLUMN_INDEX_NAME ).setPreferredWidth( DEFAULT_PROPERTY_COLUMN_WIDTH ) ;
        tableView.getColumnModel().getColumn( COLUMN_INDEX_VALUE ).setPreferredWidth( DEFAULT_VALUE_COLUMN_WIDTH ) ;
        tableView.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS ) ;
        final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent( final JTable table , final Object value , final boolean isSelected , final boolean hasFocus , final int row , final int column ) {
                final Component component = super.getTableCellRendererComponent( table , value , isSelected , hasFocus , row , column ) ;
                component.setBackground( PROPERTY_NAME_CELL_BACKGROUND_COLOR ) ;
                return component ;
            }

        } ;
        renderer.setHorizontalAlignment( SwingConstants.CENTER ) ;
        tableView.getColumnModel().getColumn( COLUMN_INDEX_NAME ).setCellRenderer( renderer ) ;
        return new JScrollPane( tableView ) ;
    }

    // @Category accessing

    @Override
    public String getBorderTitle() {
        return this.context.getMessage( "swing.FileBasedModelRepositoryViewPrimary.Title" , null , Locale.getDefault() ) ; //$NON-NLS-1$
    }

    /**
     * 現在、表示/編集対象となっているモデル要素を返す。
     *
     * @return 現在、表示/編集対象となっているモデル要素。表示/編集対象のモデル要素が無い場合は {@code null} が返される。
     */
    private FileBasedModelRepository getModelElement() {
        return (FileBasedModelRepository)( this.modelElement ) ;
    }

    @Override
    public void setModelElement( ModelElement newModelElement ) throws IllegalArgumentException {
        if ( ( null != newModelElement ) && !( newModelElement instanceof FileBasedModelRepository ) ) {
            throw new IllegalArgumentException( "newModelElement should be instance of FileBasedModelRepository or null." ) ; //$NON-NLS-1$
        }
        super.setModelElement( newModelElement ) ;
    }

    // @Category updating

    @Override
    protected void updateAll() {
        super.updateAll() ;
        this.updateBasePathField() ;
        this.updateRepositoryNameField() ;
        this.updateUuidField() ;
    }

    /**
     * BasePath の表示/編集フィールドの内容を現在のモデルの状況に合致するように更新する。
     */
    private void updateBasePathField() {
        this.tableModelGeneral.fireTableCellUpdated( ROW_INDEX_BASE_PATH , COLUMN_INDEX_VALUE ) ;
    }

    /**
     * BasePath の表示/編集フィールドの内容を現在のモデルの状況に合致するように更新する。
     */
    private void updateRepositoryNameField() {
        this.tableModelGeneral.fireTableCellUpdated( ROW_INDEX_REPOSITORY_NAME , COLUMN_INDEX_VALUE ) ;
    }

    /**
     * UUID の表示/編集フィールドの内容を現在のモデルの状況に合致するように更新する。
     */
    private void updateUuidField() {
        this.tableModelGeneral.fireTableCellUpdated( ROW_INDEX_UUID , COLUMN_INDEX_VALUE ) ;
    }

}
