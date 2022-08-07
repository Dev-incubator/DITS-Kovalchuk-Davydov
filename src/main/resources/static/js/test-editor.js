/**
 * TODO:
 *
 * - rewrite using classes
 * - add function to send custom request and rewrite all functions
 * that sending requests using new function. Here I mean next functions:
 * -- addThemeForm.addEventListener
 * -- getTestsData
 * -- submitNewTheme
 * -- deleteTheme
 * -- addNewTest
 * -- editTest
 * -- deleteTest
 * -- addNewQuestion
 *
 * -- getAnswers
 * -- deleteQuestion
 *
 *
 */

const questionFormQuestion = document.getElementById('questionFormQuestion');
const createQuestionForm = document.getElementById('createQuestionForm');
const questionFormAnswerField = document.getElementById('questionFormAnswerField');
let isNewQuestion = false;
let currentTestId = null;
let currentQuestionId = null;
let isNewTest = true;
const createNewTestForm = document.getElementById('newTestForm');
const newTestFormCloseButton = document.getElementById('newTestFormCloseButton');
const createNewTestButton = document.getElementById('createNewTestButton');
const questionModalCloseButton = document.getElementById('questionModalCloseButton');
const token = document.head.querySelector('meta[name="_csrf"]').getAttribute('content');
const detail = document.getElementById('detail');
const detailList = document.getElementById('detailList');
const addThemeForm = document.getElementById('addThemeForm');
const themeSection = document.querySelector('.theme__section');
const addThemeFormInput = document.querySelector('.add-theme-form__input');
let prevEditedTheme = null;
let prevEditedThemeValue = null;
let currentThemeId = null;
let dataByTopicId = null;
const baseUrl = window.origin;

addThemeForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const newThemeValue = addThemeFormInput.value;
    deactivateAddThemeForm();
    if (newThemeValue.length) {
        addTopic().then();
    }
});

createNewTestForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const formData = new FormData(createNewTestForm);
    const testName = formData.get('testName');
    const testDescription = formData.get('testDescription');
    if (isNewTest) {
        addNewTest(testName, testDescription).then();
    } else {
        editTest(testName, testDescription).then();
    }
    createNewTestForm.reset();
});

document.addEventListener('click', (event) => {
    const {target} = event;
    if (target.closest('#testThemes')) {
        testThemeClickHandler(event);
        deactivateAddThemeForm();
    } else if (target.closest('.sidebar-add-theme')) {
        addThemeClickHandler(target);
    } else if (target.closest('.detail__create')) {
        createTestClickHandler(event);
    } else if (target.closest('#detailList')) {
        refreshThemesValues();
        detailClickHandler(target);
        deactivateAddThemeForm();
    }
});

createQuestionForm.addEventListener('submit', (event) => {
    event.preventDefault();
    questionModalCloseButton.click();
    addNewQuestion().then();
});

createQuestionForm.addEventListener('click', ({target}) => {
    if (target.closest('.add-answer-button')) {
        createNewQuestion()
    } else if (target.closest('.answer__delete-button')) {
        target.closest('.answer').remove();
    }
});

detailList.addEventListener('click', ({target}) => {
    if (target.closest('.test')) {
        clickTestHandler(target);
    }
});

async function addTopic() {
    const url = new URL(baseUrl + "/admin/addTopic");
    const topic = {topicName: newThemeValue};
    const response = await fetch(url.toString(), {
        method: 'POST', headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": token
        },
        body: JSON.stringify(topic)
    });
    const result = await response.json();
    updateThemesList(result);
    setActiveTopic(result.length - 1);
}

async function submitNewTheme(target) {
    const themeItem = target.closest('.theme__item');
    const themeId = themeItem.dataset.id;
    const {value: topicName} = themeItem.querySelector('.theme-item__input');
    const url = new URL(baseUrl + "/admin/editTopic");
    let topic = {topicName, topicId: themeId};
    console.log(token);
    const response = await fetch(url.toString(), {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": token
        },
        body: JSON.stringify(topic)
    });
    const result = await response.json();
    updateThemesList(result);
    setActiveTopic(result.length - 1);

}

async function deleteTheme(target) {
    const themeItem = target.closest('.theme__item');
    const themeId = themeItem.dataset.id;
    const url = new URL(baseUrl + "/admin/removeTopic");
    let params = {topicId: themeId};
    url.search = new URLSearchParams(params).toString();
    const response = await fetch(url.toString(), {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": token
        }
    });
    const result = await response.json();
    updateThemesList(result);
    setActiveTopic(result.length - result.length);
}

async function getTestsData(themeId) {
    const url = new URL(baseUrl + "/admin/getTests");
    const params = {id: themeId};
    url.search = new URLSearchParams(params).toString();
    const response = await fetch(url.toString());
    let result = await response.json();
    dataByTopicId = result;
    return result;

}

async function setNewThemeTests(data) {
    console.log(data);
    let testsData;
    if (data) {
        testsData = data;
    } else {
        testsData = await getTestsData(currentThemeId);
    }
    detail.classList.add('active');
    detailList.innerHTML = '';
    console.log(testsData);
    testsData.forEach(testData => {
        detailList.appendChild(getQuestionHtml(testData))
    })

}

async function addNewTest(name, description) {
    newTestFormCloseButton.click();
    const url = new URL(baseUrl + "/admin/addTest");
    let testInfo = {name, description, topicId: currentThemeId};
    const response = await fetch(url.toString(), {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": token
        },
        body: JSON.stringify(testInfo)
    });
    const result = await response.json();
    dataByTopicId = result;
    setNewThemeTests(result).then();

}

async function editTest(name, description) {
    newTestFormCloseButton.click();
    const url = new URL(baseUrl + "/admin/editTest");
    let testInfo = {name, description, topicId: currentThemeId, testId: currentTestId};
    const response = await fetch(url.toString(), {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": token
        },
        body: JSON.stringify(testInfo)
    });
    const result = await response.json();
    setNewThemeTests(result).then();

}

async function deleteTest() {
    const url = new URL(baseUrl + "/admin/removeTest");
    let params = {topicId: currentThemeId, testId: currentTestId};
    url.search = new URLSearchParams(params).toString();
    const response = await fetch(url.toString(), {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": token
        }
    });
    const result = await response.json();
    setNewThemeTests(result).then();


}

async function addNewQuestion() {
    let data = null;
    let url = null;
    let method = null;
    const formData = new FormData(createQuestionForm);
    const questionName = formData.get('question');
    const answersData = Array.from(questionFormAnswerField.querySelectorAll('.answer')).map(answer => {
        return {
            correct: !!answer.querySelector('[name=correct]:checked'),
            answer: answer.querySelector('[name=answer]').value,
        }
    });
    if (answersData.filter((el) => el.correct === true).length < 1) {
        alert('You should choose Correct Answer')
        return;
    } else if (answersData.length < 2) {
        alert('answer is less than 2')
        return;
    }

    if (isNewQuestion) {
        url = baseUrl + '/admin/addQuestion';
        data = {
            questionName,
            topicId: currentThemeId,
            testId: currentTestId,
            answersData,
        };
        method = 'POST'
    } else {
        url = baseUrl + '/admin/editQuestionAnswers';
        data = {
            questionName,
            topicId: currentThemeId,
            questionId: currentQuestionId,
            answersData,
        };
        method = 'PUT';
    }
    isNewQuestion = false;

    const response = await fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": token
        },
        body: JSON.stringify(data)
    });
    const result = await response.json();
    setNewThemeTests(result).then();
}

async function getAnswers(questionId) {
    const url = new URL(baseUrl + "/admin/getAnswers");
    const params = {id: questionId};
    url.search = new URLSearchParams(params).toString();
    let response = await fetch(url.toString());
    return await response.json();
}

async function editQuestion() {
    const result = await getAnswers(currentQuestionId);
    openQuestionEditForm(result);
}

async function deleteQuestion() {
    const url = new URL(baseUrl + "/admin/removeQuestion");
    const params = {questionId: currentQuestionId, topicId: currentThemeId};
    url.search = new URLSearchParams(params).toString();
    let response = await fetch(url.toString(), {
        method: 'DELETE',
        headers: {
            "X-CSRF-TOKEN": token
        }
    });
    let result = await response.json();
    await setNewThemeTests(result);
    console.log('delete question')
}

function getQuestionHtml({name, testId, questions}) {
    const question = document.createElement('div');
    question.className = 'test';
    question.dataset.id = testId;
    question.innerHTML = `
    <div class="row">
      <a class="col-md-9 test__text" data-bs-toggle="collapse" href='#test${testId}' role="button" aria-expanded="false" aria-controls="collapseExample">
        ${name}
      </a>
      <button class="col-md-3 test__add-button text-md-end text-start" data-bs-toggle="modal" data-bs-target="#questionModal">new question <img class="pl-1 icon-btn" src="/img/add-icon.svg" alt="Add new question"></button>
      <div class="col-md-3 test__control text-md-end">
        <button class="test__edit-button"><img src="/img/edit-icon.svg" alt="Edit test" class="icon-btn"></button>
        <button class="test__delete-button"><img src="/img/delete-icon.svg" alt="Delete test" class="icon-btn"></button>
      </div>
    </div>
    <div class="collapse question__list mt-3" id=test${testId} data-test-id=${testId}>
      ${
        questions.reduce((accum, {questionId, description}, index) => {
            return `
            <div class="row question__item mb-2 mt-3" data-id=${questionId}>
              <span class="index-num">${index + 1}.</span>
              <div class="col-10 form-input" type="text">${description}</div>
              <div class="question-control col-2 text-end">
                <button class='question__edit-button' data-bs-toggle="modal" data-bs-target="#questionModal"><img src="/img/edit-icon.svg" alt="Edit test question" class="icon-btn"></button>
                <button class='question__delete-button'><img src="/img/delete-icon.svg" alt="Delete test question" class="icon-btn"></button>
              </div>
            </div>
          `
        }, '')
    }
    </div>
  `
    return question

}

function changeActiveAddThemeFormStatus() {
    addThemeForm.classList.toggle('active');
    addThemeForm.previousElementSibling.classList.toggle('active');

}

function addThemeClickHandler(target) {
    if (target.closest('.sidebar-add-theme__button')) {
        changeActiveAddThemeFormStatus();
    }

}

function deactivateAddThemeForm() {
    addThemeForm.reset();
    addThemeForm.classList.remove('active');

}

function testThemeClickHandler(event) {
    event.preventDefault();
    const {target} = event;
    const themeItem = target.closest('.theme-item');
    if (themeItem) {
        const themeId = themeItem.dataset.id;
        if (target.closest('.theme-item__input')) {
            if (themeId !== currentThemeId) {
                detail.classList.remove('active');
                currentThemeId = themeId;
                const themeName = themeItem.querySelector('.theme-item__input').value;
                setThemeTitle(themeName);
                setNewThemeTests().then();
            }
        } else if (target.closest('.theme-item__edit')) {
            setThemeEditMode(themeItem);
        } else if (target.closest('.theme-item__submit')) {
            submitNewTheme(target).then();
        } else if (target.closest('.theme-item__delete')) {
            deleteTheme(target).then();
        }
    }
}

function setThemeTitle(newName) {
    document.querySelector('.detail__title').textContent = newName;
}

function setCreateTestFormStartData() {
    if (dataByTopicId != null && dataByTopicId.length !== 0) {
        const {name, description} = dataByTopicId.find(({testId}) => {
            return testId == currentTestId
        });
        createNewTestForm.querySelector('[name=testName]').value = name;
        createNewTestForm.querySelector('[name=testDescription]').value = description;
    }

}

function createTestClickHandler(event) {
    const {target, isTrusted} = event;
    console.log(event)
    const openFormButton = target.closest('#createNewTestButton');
    if (openFormButton) {
        createNewTestForm.reset();
    }
    if (!isTrusted) {
        setCreateTestFormStartData();
        isNewTest = false;
    } else {
        isNewTest = true;
    }

}

function setThemeEditMode(newTheme) {
    if (prevEditedTheme) {
        prevEditedTheme.classList.remove('edit');
        prevEditedTheme.querySelector('.theme-item__input').setAttribute('readonly', '');
        prevEditedTheme.querySelector('.theme-item__input').value = prevEditedThemeValue;
    }
    prevEditedTheme = newTheme;
    if (newTheme) {
        newTheme.classList.add('edit');
        newTheme.querySelector('.theme-item__input').removeAttribute('readonly');
        newTheme.querySelector('.theme-item__input').focus();
        prevEditedThemeValue = newTheme.querySelector('.theme-item__input').value;
    }

}

function refreshThemesValues() {
    setThemeEditMode(null);
    prevEditedThemeValue = null;

}

/**
 * TODO:
 * - If you want to use string as HTML code that will be parsed
 * it is a good idea to move it in separate function with
 * appropriate name
 *
 * - Check all document on such mistake
 *
 *  */

function updateThemesList(data) {
    let lastElementIndex = null;
    themeSection.innerHTML = '';
    themeSection.innerHTML = `
    ${data.map(({topicName, topicId}, index) => {
        lastElementIndex = index;
        return `
        <div class="theme__item theme-item d-flex" data-id=${topicId} >
        <input class="theme-item__input" data-id='topicItem${index}' value="${topicName}" readonly="">
        <span class="theme-item__control">
          <button class="theme-item__submit"><img src="/img/submit-icon.svg" alt=""></button>
          <button class="theme-item__edit"><img src="/img/edit-icon.svg" alt=""></button>
          <button class="theme-item__delete"><img src="/img/delete-icon.svg" alt=""></button>
        </span>
      </div>
      `
    }).join('')}
  `
}

function setActiveTopic(index) {
    document.getElementById('testThemes').children.item(0).children.item(index).children.item(0).click();
}

function refreshQuestionForm() {
    questionFormQuestion.textContent = '';
    questionFormAnswerField.textContent = '';
}

function openQuestionCreateForm() {
    isNewQuestion = true;
    refreshQuestionForm();
}

function getNewAnswerField(data) {
    const nextAnswerNumber = questionFormAnswerField.childNodes.length;
    const answer = document.createElement('div');
    answer.className = 'answer w-100';
    const description = data ? data.description : '';
    const correct = data ? data.correct : false;
    answer.innerHTML = `
        <input class="answer__correct" name="correct" type="checkbox" ${correct ? 'checked' : ''} id="answer_${nextAnswerNumber}">
        <label class="col-12 answer__title py-2"  for="answer_${nextAnswerNumber}">Answer ${nextAnswerNumber + 1}</label>
        <input class="col-11 answer-input" name="answer" type="text" value="${description}" placeholder="write answer"  required>
        <button class="col-1 answer__delete-button" type="button"><img src="/img/delete-icon.svg" alt="delete"></button>
  `;
    return answer;
}


function openQuestionEditForm({description, answerDTOList}) {
    refreshQuestionForm();
    questionFormQuestion.textContent = description;
    answerDTOList.forEach(itemData => {
        questionFormAnswerField.append((getNewAnswerField(itemData)));
    })
}

function setCurrentTestId(target) {
    currentTestId = target.closest('.test').dataset.id;
}

function setCurrentQuestionId(target) {
    currentQuestionId = target.closest('.question__item').dataset.id;
    console.log('Set current questionId', currentQuestionId);
}

function questionClickHandler(target) {
    setCurrentQuestionId(target);
    if (target.closest('.question__edit-button')) {
        editQuestion().then();
    } else if (target.closest('.question__delete-button')) {
        deleteQuestion().then();
    }
}

function detailClickHandler(target) {
    console.log()
    if (target.closest('.question__item')) {
        questionClickHandler(target);
    }
}

function createNewQuestion() {
    questionFormAnswerField.append(getNewAnswerField());
}

function clickTestHandler(target) {
    setCurrentTestId(target);
    if (target.classList.contains('test__text')) {
        target.closest('.test').classList.toggle('open');
    } else if (target.closest('.test__add-button')) {
        openQuestionCreateForm();
        createQuestionForm.reset();
    } else if (target.closest('.test__edit-button')) {
        isNewTest = false;
        createNewTestButton.click();
    } else if (target.closest('.test__delete-button')) {
        deleteTest().then();
    }
}

document.getElementById('topicItem0').click();