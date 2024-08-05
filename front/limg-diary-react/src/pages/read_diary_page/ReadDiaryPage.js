import css from './ReadDiaryPage.module.scss'

import { useNavigate, useParams } from 'react-router-dom'

import Header from '../global_component/header/Header';
import Footer from '../global_component/fotter/Fotter';
import WhiteBox from '../global_component/white_box/WhiteBox';
import { NoActionBlueTag } from '../global_component/tag/Tag';
import Modal from '../global_component/modal/Modal';
import { RemoveableGrayTag } from '../global_component/tag/Tag';

import DefaultDiaryImg from '../resource/img/default_diary_img.png'

import { useRef, useState } from 'react';
import { DoubleSmallButton, SingleSmallButton, TextButton } from '../global_component/button/Button';

function ReadDiaryPage() {
    const { diaryid } = useParams();

    // ref
    const bodyRef = useRef()

    const tagArr = ['asdf', 'dbhaskfaslga', 'a a a', 'ee', ';dddd']


    // modal
    const [tagChangeModalState, setTagChangeModalState] = useState(false)

    return (
        <div id={css.root_container} className={css.page_root_container}>
            <Header parentBodyRef={bodyRef} />
            <Footer parentBodyRef={bodyRef} />

            <div id={css.body_container} className={css.container} ref={bodyRef}>
                {/* 오늘 날짜 */}
                <div id={css.date}>2024.07.20 금요일</div>

                {/* 하루 평가 */}
                <div id={css.today_rate_container} className={css.container}>
                    <div id={css.today_rate_title}>오늘 하루 기분: </div>
                    <div id={css.today_rate_space} />
                    <div id={css.today_rate_rate}>평범해요</div>
                </div>

                {/* 그림 */}
                <div id={css.diary_img_box}><img src={DefaultDiaryImg} /></div>

                {/* 일기 */}
                <div id={css.diary_content_container}>
                    <WhiteBox title={'오늘의 일기'} child={
                        <div id={css.diary_content_box}>asdfas dfsdfas gkjasdh falsh jfklasdhf lsdl kahggsa sdgfs dfa sfdsghs용<br />
                            일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />
                            일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />
                            일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />
                            일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />일기 내용<br />
                            일기 내용<br /></div>
                    } />
                </div>

                {/* 태그 */}
                <div id={css.tag_title_container} className={css.container}>
                    <div id={css.tag_title}>태그</div>
                    <button id={css.tag_change_btn} onClick={() => {
                        setTagChangeModalState(tagChangeModalState ? false : true)
                    }}>태그 변경</button>
                </div>
                <div id={css.tag_box} className={css.container}>
                    {tagArr.map((tag, idx) => (
                        <NoActionBlueTag tag={tag} />
                    ))}
                </div>
            </div>

            {/* 태그 변경 */}
            {tagChangeModalState && <Modal title={'태그 변경'} body={
                <TagChangeModalBody currentTagArr={tagArr} />
            } modalOffHandle={() => setTagChangeModalState(false)} />}
        </div>
    )
}


function TagChangeModalBody({ currentTagArr }) {

    // navigate
    const navigate = useNavigate()

    // ref
    const tagInputRef = useRef()

    // input str
    let tagInputStr = ''

    // list
    const [tagArr, setTagArr] = useState(currentTagArr)

    return (
        <div id={css.tag_change_modal_body} className={css.container}>
            {/* 태그 추가 */}
            <div id={css.tag_add_container} className={css.container}>
                <div id={css.tag_change_title}>태그</div>
                <div id={css.tag_input_container} className={css.container}>
                    {/* 인풋 */}
                    <input id={css.tag_input} placeholder='#' ref={tagInputRef} onChange={(e) => {
                        // 첫글자 공백 제거
                        while (e.target.value.length > 0 && e.target.value[0] === ' ') {
                            e.target.value = e.target.value.substring(1)
                        }
                        tagInputStr = e.target.value
                    }} />
                    {/* 버튼 */}
                    <button id={css.tag_add_btn} onClick={(e) => {
                        // 한글자 이상일때만 리스트에 추가
                        if (tagInputStr.length > 0) {
                            tagInputStr = tagInputStr[0] === '#' ? tagInputStr : '#' + tagInputStr
                            // check already exist
                            if (!tagArr.includes(tagInputStr)) {
                                // update state
                                tagArr.push(tagInputStr)
                                setTagArr(Array.from(tagArr))
                            }
                            // clear input
                            tagInputRef.current.value = ''
                        }
                    }}>Add</button>
                </div>
            </div>

            {/* 추가된 태그 */}
            <div id={css.tags_container} className={css.container}>
                {tagArr.map((tag, idx) => (
                    <RemoveableGrayTag tag={tag} func={() => {
                        tagArr.splice(idx, 1)
                        setTagArr(Array.from(tagArr))
                    }} />
                ))}
            </div>

            {/* 버튼 */}
            <div id={css.tag_change_btn_container} className={css.container}>
                <SingleSmallButton text={'변경 완료'} func={()=>{
                    window.location.reload();
                }}/>
            </div>
        </div>
    )
}
export default ReadDiaryPage