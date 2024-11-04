/**
 * File: preorder_traversal_iii_template.js
 * Created Time: 2023-05-09
 * Author: Justin (xiefahit@gmail.com)
 */

const { arrToTree } = require('../modules/TreeNode');
const { printTree } = require('../modules/PrintUtil');

/* 判斷當前狀態是否為解 */
function isSolution(state) {
    return state && state[state.length - 1]?.val === 7;
}

/* 記錄解 */
function recordSolution(state, res) {
    res.push([...state]);
}

/* 判斷在當前狀態下，該選擇是否合法 */
function isValid(state, choice) {
    return choice !== null && choice.val !== 3;
}

/* 更新狀態 */
function makeChoice(state, choice) {
    state.push(choice);
}

/* 恢復狀態 */
function undoChoice(state) {
    state.pop();
}

/* 回溯演算法：例題三 */
function backtrack(state, choices, res) {
    // 檢查是否為解
    if (isSolution(state)) {
        // 記錄解
        recordSolution(state, res);
    }
    // 走訪所有選擇
    for (const choice of choices) {
        // 剪枝：檢查選擇是否合法
        if (isValid(state, choice)) {
            // 嘗試：做出選擇，更新狀態
            makeChoice(state, choice);
            // 進行下一輪選擇
            backtrack(state, [choice.left, choice.right], res);
            // 回退：撤銷選擇，恢復到之前的狀態
            undoChoice(state);
        }
    }
}

// Driver Code
const root = arrToTree([1, 7, 3, 4, 5, 6, 7]);
console.log('\n初始化二元樹');
printTree(root);

// 回溯演算法
const res = [];
backtrack([], [root], res);

console.log('\n輸出所有根節點到節點 7 的路徑，要求路徑中不包含值為 3 的節點');
res.forEach((path) => {
    console.log(path.map((node) => node.val));
});
