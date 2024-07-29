import MakeQuestionContent from "./MakeQuestionlContent"

const MakeQuestion = () => {
    return (
        <div className="mb-20">
        {[0, 1, 2, 3, 4, 5, 6].map((index) => (
            <MakeQuestionContent key={index} question="같이 밥먹고 싶은 사람은?"/>
          ))}
        </div>       
)
}

export default MakeQuestion