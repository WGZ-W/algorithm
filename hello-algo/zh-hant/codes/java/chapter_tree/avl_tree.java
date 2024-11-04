/**
 * File: avl_tree.java
 * Created Time: 2022-12-10
 * Author: krahets (krahets@163.com)
 */

package chapter_tree;

import utils.*;

/* AVL 樹 */
class AVLTree {
    TreeNode root; // 根節點

    /* 獲取節點高度 */
    public int height(TreeNode node) {
        // 空節點高度為 -1 ，葉節點高度為 0
        return node == null ? -1 : node.height;
    }

    /* 更新節點高度 */
    private void updateHeight(TreeNode node) {
        // 節點高度等於最高子樹高度 + 1
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    /* 獲取平衡因子 */
    public int balanceFactor(TreeNode node) {
        // 空節點平衡因子為 0
        if (node == null)
            return 0;
        // 節點平衡因子 = 左子樹高度 - 右子樹高度
        return height(node.left) - height(node.right);
    }

    /* 右旋操作 */
    private TreeNode rightRotate(TreeNode node) {
        TreeNode child = node.left;
        TreeNode grandChild = child.right;
        // 以 child 為原點，將 node 向右旋轉
        child.right = node;
        node.left = grandChild;
        // 更新節點高度
        updateHeight(node);
        updateHeight(child);
        // 返回旋轉後子樹的根節點
        return child;
    }

    /* 左旋操作 */
    private TreeNode leftRotate(TreeNode node) {
        TreeNode child = node.right;
        TreeNode grandChild = child.left;
        // 以 child 為原點，將 node 向左旋轉
        child.left = node;
        node.right = grandChild;
        // 更新節點高度
        updateHeight(node);
        updateHeight(child);
        // 返回旋轉後子樹的根節點
        return child;
    }

    /* 執行旋轉操作，使該子樹重新恢復平衡 */
    private TreeNode rotate(TreeNode node) {
        // 獲取節點 node 的平衡因子
        int balanceFactor = balanceFactor(node);
        // 左偏樹
        if (balanceFactor > 1) {
            if (balanceFactor(node.left) >= 0) {
                // 右旋
                return rightRotate(node);
            } else {
                // 先左旋後右旋
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        // 右偏樹
        if (balanceFactor < -1) {
            if (balanceFactor(node.right) <= 0) {
                // 左旋
                return leftRotate(node);
            } else {
                // 先右旋後左旋
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        // 平衡樹，無須旋轉，直接返回
        return node;
    }

    /* 插入節點 */
    public void insert(int val) {
        root = insertHelper(root, val);
    }

    /* 遞迴插入節點（輔助方法） */
    private TreeNode insertHelper(TreeNode node, int val) {
        if (node == null)
            return new TreeNode(val);
        /* 1. 查詢插入位置並插入節點 */
        if (val < node.val)
            node.left = insertHelper(node.left, val);
        else if (val > node.val)
            node.right = insertHelper(node.right, val);
        else
            return node; // 重複節點不插入，直接返回
        updateHeight(node); // 更新節點高度
        /* 2. 執行旋轉操作，使該子樹重新恢復平衡 */
        node = rotate(node);
        // 返回子樹的根節點
        return node;
    }

    /* 刪除節點 */
    public void remove(int val) {
        root = removeHelper(root, val);
    }

    /* 遞迴刪除節點（輔助方法） */
    private TreeNode removeHelper(TreeNode node, int val) {
        if (node == null)
            return null;
        /* 1. 查詢節點並刪除 */
        if (val < node.val)
            node.left = removeHelper(node.left, val);
        else if (val > node.val)
            node.right = removeHelper(node.right, val);
        else {
            if (node.left == null || node.right == null) {
                TreeNode child = node.left != null ? node.left : node.right;
                // 子節點數量 = 0 ，直接刪除 node 並返回
                if (child == null)
                    return null;
                // 子節點數量 = 1 ，直接刪除 node
                else
                    node = child;
            } else {
                // 子節點數量 = 2 ，則將中序走訪的下個節點刪除，並用該節點替換當前節點
                TreeNode temp = node.right;
                while (temp.left != null) {
                    temp = temp.left;
                }
                node.right = removeHelper(node.right, temp.val);
                node.val = temp.val;
            }
        }
        updateHeight(node); // 更新節點高度
        /* 2. 執行旋轉操作，使該子樹重新恢復平衡 */
        node = rotate(node);
        // 返回子樹的根節點
        return node;
    }

    /* 查詢節點 */
    public TreeNode search(int val) {
        TreeNode cur = root;
        // 迴圈查詢，越過葉節點後跳出
        while (cur != null) {
            // 目標節點在 cur 的右子樹中
            if (cur.val < val)
                cur = cur.right;
            // 目標節點在 cur 的左子樹中
            else if (cur.val > val)
                cur = cur.left;
            // 找到目標節點，跳出迴圈
            else
                break;
        }
        // 返回目標節點
        return cur;
    }
}

public class avl_tree {
    static void testInsert(AVLTree tree, int val) {
        tree.insert(val);
        System.out.println("\n插入節點 " + val + " 後，AVL 樹為");
        PrintUtil.printTree(tree.root);
    }

    static void testRemove(AVLTree tree, int val) {
        tree.remove(val);
        System.out.println("\n刪除節點 " + val + " 後，AVL 樹為");
        PrintUtil.printTree(tree.root);
    }

    public static void main(String[] args) {
        /* 初始化空 AVL 樹 */
        AVLTree avlTree = new AVLTree();

        /* 插入節點 */
        // 請關注插入節點後，AVL 樹是如何保持平衡的
        testInsert(avlTree, 1);
        testInsert(avlTree, 2);
        testInsert(avlTree, 3);
        testInsert(avlTree, 4);
        testInsert(avlTree, 5);
        testInsert(avlTree, 8);
        testInsert(avlTree, 7);
        testInsert(avlTree, 9);
        testInsert(avlTree, 10);
        testInsert(avlTree, 6);

        /* 插入重複節點 */
        testInsert(avlTree, 7);

        /* 刪除節點 */
        // 請關注刪除節點後，AVL 樹是如何保持平衡的
        testRemove(avlTree, 8); // 刪除度為 0 的節點
        testRemove(avlTree, 5); // 刪除度為 1 的節點
        testRemove(avlTree, 4); // 刪除度為 2 的節點

        /* 查詢節點 */
        TreeNode node = avlTree.search(7);
        System.out.println("\n查詢到的節點物件為 " + node + "，節點值 = " + node.val);
    }
}
