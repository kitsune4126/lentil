package com.mamezou.lentil.swing ;

import java.util.Locale ;

import javax.swing.JComponent ;
import javax.swing.JScrollPane ;
import javax.swing.table.AbstractTableModel ;

import org.springframework.context.ConfigurableApplicationContext ;

import com.mamezou.lentil.model.FileBasedModelRepository ;

/**
 * {@link FileBasedModelRepository} 用の主プロパティ・ビュー。
 *
 * @author kitsune
 */
public class FileBasedModelRepositoryViewPrimary extends PropertyView< FileBasedModelRepository > {

    // @Category constant definitions

    /**
     * BasePath フィールドの行インデックス。
     */
    private static final int ROW_INDEX_BASE_PATH = 0 ;

    /**
     * RepositoryName フィールドの行インデックス。
     */
    private static final int ROW_INDEX_REPOSITORY_NAME = 1 ;

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
                if ( COLUMN_INDEX_FOR_NAME == columnIndex ) {
                    return propertyNames[ rowIndex ] ;
                } else {
                    final FileBasedModelRepository repository = getTargetModel() ;
                    switch ( rowIndex ) {
                    case ROW_INDEX_BASE_PATH :
                        return ( null == repository ) ? null : repository.getBasePath() ;
                    case ROW_INDEX_REPOSITORY_NAME :
                        return ( null == repository ) ? null : repository.getName() ;
                    default :
                        return null ;
                    }
                }
            }

            @Override
            public boolean isCellEditable( final int rowIndex , final int columnIndex ) {
                return ( COLUMN_INDEX_FOR_NAME != columnIndex ) && ( null != getTargetModel() ) && ( ROW_INDEX_BASE_PATH != rowIndex ) ;
            };

            @Override
            public void setValueAt( final Object aValue , final int rowIndex , final int columnIndex ) {
                if ( COLUMN_INDEX_FOR_VALUE == columnIndex ) {
                    switch ( rowIndex ) {
                    case ROW_INDEX_REPOSITORY_NAME :
                        getTargetModel().setName( ( (String)( aValue ) ).trim() ) ;
                    }
                }
            };

        } ;
        return new JScrollPane( this.createPropertyTableView( this.tableModelGeneral ) ) ;
    }

    // @Category accessing

    @Override
    public String getBorderTitle() {
        return this.context.getMessage( "swing.FileBasedModelRepositoryViewPrimary.Title" , null , Locale.getDefault() ) ; //$NON-NLS-1$
    }

    // @Category updating

    @Override
    protected void updateAll() {
        super.updateAll() ;
        this.updateBasePathField() ;
        this.updateRepositoryNameField() ;
    }

    /**
     * BasePath の表示/編集フィールドの内容を現在のモデルの状況に合致するように更新する。
     */
    private void updateBasePathField() {
        this.tableModelGeneral.fireTableCellUpdated( ROW_INDEX_BASE_PATH , COLUMN_INDEX_FOR_VALUE ) ;
    }

    /**
     * BasePath の表示/編集フィールドの内容を現在のモデルの状況に合致するように更新する。
     */
    private void updateRepositoryNameField() {
        this.tableModelGeneral.fireTableCellUpdated( ROW_INDEX_REPOSITORY_NAME , COLUMN_INDEX_FOR_VALUE ) ;
    }

}
