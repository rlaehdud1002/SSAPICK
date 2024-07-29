import MakeQuestionContent from "./MakeQuestionlContent"

const MakeQuestion = () => {
   const testList = [
      { question: "같이 밥먹고 싶은 사람은?"},
      { question: "같이 커피 마시고 싶은 사람은?"}
   ]
    return (
      
        <div className="mb-20">
         <MakeQuestionContent testList={[]}/>
        {/* {[0, 1].map((index) => (
            <MakeQuestionContent key={index} question="같이 밥먹고 싶은 사람은?"/>
          ))} */}
        </div>       
)
}

export default MakeQuestion