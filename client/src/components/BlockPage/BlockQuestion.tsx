import BlockQuestionContent from "./BlockQuestionContent"

const BlockQuestion = () => {
    return (
        <div className="mb-20">
        {[0, 1, 2, 3, 4, 5, 6, 7, 8, 9].map((index) => (
            <BlockQuestionContent key={index} question="같이 밥먹고 싶은 사람은?"/>
          ))}
        </div>

    )
}

export default BlockQuestion