const UserAddInfo = () => {
    return <div>
        <h3 style={{fontSize:13}}>추가 정보 입력</h3>
        <div style={{fontSize:13}}>추후 힌트로 사용됩니다.</div>
        <div style={{color:"red", fontSize:10}}>최소 2개의 정보 입력이 필수입니다.</div>
        <div className="flex flex-col items-center">
            <div>
            <div>
                <label htmlFor="mbti">MBTI </label>
                <input className="rounded-md bg-white/50 border border-indigo-950" type="text" name="mbti"/>
            </div>
            <div>
                <label htmlFor="major">전공 </label>
                <input className="rounded-md bg-white/50 border border-indigo-950" type="text" name="major"/>
            </div>
            <div>
                <label htmlFor="birth">생년월일 </label>
                <input className="rounded-md bg-white/50 border border-indigo-950" type="text" name="birth"/>
            </div>
            <div>
                <label htmlFor="town">동네 </label>
                <input className="rounded-md bg-white/50 border border-indigo-950" type="text" name="town"/>
            </div>
            <div>
                <label htmlFor="hobby">관심사 </label>
                <input className="rounded-md bg-white/50 border border-indigo-950" type="text" name="hobby"/>
            </div>
            <div>
                <button type="button" className="text-white bg-blue-500 font-medium rounded-lg text-sm px-5 py-2.5 " >완료</button>
            </div>
        </div>
    </div>
    </div>

}

export default UserAddInfo