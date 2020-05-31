package com.mamezou.lentil.model ;

import java.io.IOException ;
import java.util.UUID ;

/**
 * モデル・リポジトリのための抽象インタフェース。
 *
 * @author kitsune@mamezou.com
 */
public interface ModelRepository {

    // @Category accessing

    /**
     * 新たなモデル要素をリポジトリに追加する。
     *
     * @param element 追加されるモデル要素。
     * @param asTopElement 指定されたモデル要素をこのリポジトリの最上位要素として追加するか否か。最上位要素なら {@code true} 、そうでなければ {@code false} 。
     * @throws ElementAlreadyExistsException 指定されたモデル要素が既に登録されていた場合。
     * @throws IllegalArgumentException {@code element} として {@code null} が渡された場合。
     */
    public void addElement( final ElementBody element , final boolean asTopElement ) throws ElementAlreadyExistsException , IllegalArgumentException ;

    /**
     * 指定されたキーに対応するモデル要素を取り出して返す。
     *
     * @param key 指定されたキー。
     * @return 指定されたキーに対応するモデル要素。
     * @throws ElementNotFoundException 指定されたキーに対応するモデル要素が無い(またはリトリーブできなかった)時。
     * @throws IllegalArgumentException {@code key} として {@code null} が渡された場合。
     */
    public ElementBody getElement( final UUID key ) throws ElementNotFoundException , IllegalArgumentException ;

    /**
     * メタ・リポジトリ(このリポジトリに含まれるモデル要素群の一段メタな型定義をしているモデル要素を含むリポジトリ)を返す。
     *
     * @return メタ・リポジトリ。
     */
    public ModelRepository getMetaRepository() ;

    /**
     * 指定されたキーの登録とそのキーに対応するモデル要素を削除する。
     *
     * @param key 指定されたキー。
     * @throws ElementNotFoundException 指定されたキーに対応するモデル要素が無い場合。
     * @throws IllegalArgumentException {@code key} として {@code null} が渡された場合。
     */
    public void removeElement( final UUID key ) throws ElementNotFoundException , IllegalArgumentException ;

    // @Category persistency handling

    /**
     * レシーバが管理しているモデル要素のうち、下層の永続化層に対して追加/更新/削除が必要なものを永続化する。
     *
     * @throws IOException 入出力関連の例外が発生した場合。
     */
    public void flush() throws IOException ;

    /**
     * レシーバがメモリ上に保持しているすべてのモデル要素を破棄して、以後アクセスされるモデル要素を下層の永続化層から読み込みなおすようにする。
     */
    public void reflesh() ;

    /**
     * レシーバ自身とレシーバが管理している下層の永続化層に対して追加/更新/削除が必要なモデル要素群を永続化する。
     *
     * @throws IOException 入出力関連の例外が発生した場合。
     */
    public void save() throws IOException ;

}
