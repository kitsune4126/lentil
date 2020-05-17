package com.mamezou.lentil.repository ;

import java.util.UUID ;

import com.mamezou.lentil.model.ModelElement ;
import com.mamezou.lentil.swing.BrowsableElement ;

/**
 * モデル・リポジトリのための抽象インタフェース。
 *
 * @author kitsune@mamezou.com
 */
public interface ModelRepository extends BrowsableElement {

    // @Category accessing

    /**
     * 指定されたキーに対応するモデル要素を取り出して返す。
     *
     * @param key 指定されたキー。
     * @return 指定されたキーに対応するモデル要素。
     * @throws ElementNotFoundException 指定されたキーに対応するモデル要素が無い(またはリトリーブできなかった)時。
     */
    public ModelElement getModelElement( final UUID key ) throws ElementNotFoundException ;

    /**
     * 新たなモデル要素をリポジトリに追加する。
     *
     * @param key 追加されるモデル要素のキー。
     * @param element 追加されるモデル要素。
     * @throws ElementAlreadyExistsException 指定されたキーに対応する要素が既に登録されていた場合。
     */
    public void putModelElement( final UUID key , final ModelElement element ) throws ElementAlreadyExistsException ;

    /**
     * 指定されたキーの登録とそのキーに対応するモデル要素を削除する。
     *
     * @param key 指定されたキー。
     * @throws ElementNotFoundException 指定されたキーに対応するモデル要素が無い場合。
     */
    public void removeModelElement( final UUID key ) throws ElementNotFoundException ;

}
