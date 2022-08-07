/**
 * TODO:
 * - Add classes
 * - rename this file
 * 
 */

const themeSelect = document.getElementById('themeSelect');
const resultTableBody = document.getElementById('resultTableBody');
const dataContainer = document.getElementById('dataContainer');
let isReverseTest = false;
const baseUrl = window.origin;

themeSelect.addEventListener('change', ({target}) => {
    dataContainer.classList.remove('active');
    try {
        getTestStatistics(target).then();
    } catch (err) {
        console.error(err)
    }
});

dataContainer.addEventListener('click', (event) => {
    const {target} = event;
    // TODO: fix this class name
    if (target.closest('#sortTestsButton')) {
        isReverseTest = !isReverseTest;
        reverseTests(target);
    } else if (target.closest('.question')) {
        questionClickHandler(target)
    }
});

async function getTestStatistics(target) {
    const themeId = target.value;
    const url = new URL(baseUrl + "/admin/getTestsStatistic");
    const params = {id: themeId};
    url.search = new URLSearchParams(params).toString();
    const response = await fetch(url.toString());
    const result = await response.json();
    updateResult(result);
}

function updateResult(data) {
    if (!data) {
        return
    }
    // TODO:
    // if you want to parse string below as HTML
    // it is a good idea to move it in separate function
    // to split the idea from the implementation

    const reversedData = isReverseTest ? [...data].reverse() : [...data];
    dataContainer.classList.add('active');
    resultTableBody.innerHTML = `
    ${reversedData.map(({testName, count, avgProc, questionStatistics}, index) => {
        return `
      <div class="container test" data-id="${index}">
        <div class="row result-row" data-bs-toggle="collapse" href='#test${index}'>
          <div class="col-md-9 col-sm-7 text-start">${testName}</div>
          <div class="col-md-3 col-sm-5 text-center d-flex justify-content-between">
              <div style="width: 40px;">${count}</div>
              <div style="width: 100px;">${avgProc}%</div>
          </div> 
        </div>
        <div class="collapse" id='test${index}'>
          <div class="question">
            <div class="row question__head">
              <span class="col-sm-8 text-start question__head-text">Questions</span>
              <div class="col-sm-3 offset-sm-1 text-sm-end text-start question__head-text">
                <span>Correct</span>
                <button class="sort-button"><img src="/img/sort-icon.svg" alt="sort"></button>
              </div>
            </div>
            <hr class="green_line">
            <div class="question__list">
              ${questionStatistics.map(({questionDescription, avgProc}) => {
            return `
                  <div class="mt-3 question__item row">
                    <div class="col-sm-9 question__name textrea_autoheight" readonly>${questionDescription}</div>
                    <span class="text-sm-end text-start col-sm-3 question__proc">${avgProc}%</span>
                  </div>
                `
        }).join('')}
            </div>
          </div>
        </div>
      </div>
      `
    }).join('')}
  `
}

function reverseTests(target) {
    target.closest('#sortTestsButton').classList.toggle('reverse');
    const reversedTests = Array.from(resultTableBody.querySelectorAll('.test')).reverse();
    resultTableBody.innerHTML = '';
    reversedTests.forEach(test => {
        resultTableBody.append(test);
    })
}

function reverseQuestions(target) {
    const questionNode = target.closest('.question')
    const questionsContainer = questionNode.querySelector('.question__list');
    const questions = questionsContainer.querySelectorAll('.question__item');
    const reversedQuestions = [...questions].reverse();
    questionsContainer.innerHTML = '';
    reversedQuestions.forEach(item => {
        questionsContainer.append(item);
    })
}

function questionClickHandler(target) {
    if (target.closest('.sort-button')) {
        reverseQuestions(target);
    }
}